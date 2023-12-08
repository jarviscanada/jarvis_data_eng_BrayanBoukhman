package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountJpaRepository;
import ca.jrvs.apps.trading.dao.PositionJpaRepository;
import ca.jrvs.apps.trading.dao.SecurityOrderJpaRepository;
import ca.jrvs.apps.trading.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final AccountJpaRepository accountJpaRepository;

    private final SecurityOrderJpaRepository securityOrderJpaRepository;

    private final QuoteService quoteService;

    private final PositionJpaRepository positionJpaRepository;

    @Autowired
    public OrderService(AccountJpaRepository accountJpaRepository, SecurityOrderJpaRepository securityOrderJpaRepository, QuoteService quoteService, PositionJpaRepository positionJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
        this.securityOrderJpaRepository = securityOrderJpaRepository;
        this.quoteService = quoteService;
        this.positionJpaRepository = positionJpaRepository;
    }

    /**
     * Execute a market order
     * - validate the order (e.g. size and ticker)
     * - create a securityOrder
     * - handle buy or sell orders
     * 	- buy order : check account balance
     * 	- sell order : check position for the ticker/symbol
     * 	- do not forget to update the securityOrder.status
     * - save and return securityOrder
     *
     * NOTE: you are encouraged to make some helper methods (protected or private)
     *
     * @param marketOrder market order
     * @return SecurityOrder from security_order table
     * @throws DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException for invalid inputs
     */
    public SecurityOrder executeMarketOrder(MarketOrder marketOrder) {
        // Validate the market order
        validateMarketOrder(marketOrder);

        // Create a security order
        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccount(findAccountById(marketOrder.getTraderId()));
        securityOrder.setQuote(quoteService.saveQuoteByTicker(marketOrder.getTicker()));
        securityOrder.setSize(marketOrder.getSize());
        securityOrder.setPrice(securityOrder.getPrice());
        securityOrder.setStatus("PENDING");

        // Handle buy or sell orders
        if (marketOrder.getSize() > 0) {
            handleBuyMarketOrder(securityOrder);
        } else {
            handleSellMarketOrder(securityOrder);
        }

        // Save and return security order
        return securityOrderJpaRepository.save(securityOrder);
    }

    // Helper method to validate the market order
    private void validateMarketOrder(MarketOrder marketOrder) {
        // Add your validation logic here
        // For example, check if the size and ticker are valid
        if (marketOrder.getSize() == 0 || marketOrder.getTicker() == null || marketOrder.getTicker().isEmpty() || !accountJpaRepository.existsById(marketOrder.getTraderId())) {
            throw new IllegalArgumentException("Invalid market order");
        }
    }

    protected void handleBuyMarketOrder(SecurityOrder securityOrder) {

        double orderValue = securityOrder.getSize() * securityOrder.getPrice();
        if (securityOrder.getAccount().getAmount() < orderValue) {
            throw new IllegalArgumentException("Insufficient funds to execute buy order");
        }
        // Update security order status
        securityOrder.setStatus("FILLED");
    }

    /**
     * Helper method to execute a sell order
     * I make the assumption that if total orders that user holds is greater than amount sold then a new security order can be made to sell
     */
    protected void handleSellMarketOrder(SecurityOrder securityOrder) {
        // Retrieve the current position for the specified ticker
        Position position = positionJpaRepository.findByAccountIdAndTicker(securityOrder.getAccount().getId(), securityOrder.getQuote().getTicker());

        if (position != null) {
            // Check if the user has enough shares to sell
            if (position.getPosition() >= securityOrder.getSize()) {
                // Update the position and account accordingly
                Account account = securityOrder.getAccount();
                account.setAmount(account.getAmount() + securityOrder.getPrice() * securityOrder.getSize());

                accountJpaRepository.save(account);

                // NOTE as we are selling i set the size to a negative value to signify this.
                securityOrder.setSize(-securityOrder.getSize());
                // Update security order status to "FILLED"
                securityOrder.setStatus("FILLED");
            } else {
                // Not enough shares to sell
                securityOrder.setStatus("REJECTED"); // Or handle as appropriate
            }
        } else {
            // No existing position for the specified ticker
            securityOrder.setStatus("REJECTED");
        }
    }

    // Helper method to find account by traderId
    private Account findAccountById(int traderId) {
        return accountJpaRepository.findById(traderId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for traderId: " + traderId));
    }
}
