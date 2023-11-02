package ca.jrvs.apps.jdbc.controller;

import ca.jrvs.apps.jdbc.dao.PositionDAO;
import ca.jrvs.apps.jdbc.dao.QuoteDAO;
import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helper.DatabaseConnectionManager;
import ca.jrvs.apps.jdbc.service.PositionService;
import ca.jrvs.apps.jdbc.service.QuoteService;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class StockQuoteController {

    private QuoteService quoteService;
    private PositionService positionService;

    public void initClient() throws SQLException {
        Connection connection = DatabaseConnectionManager.getConnection();
        this.positionService = new PositionService(new PositionDAO(connection));
        this.quoteService = new QuoteService(new QuoteDAO(connection));
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int input = getUserInput(scanner);

            switch (input) {
                case 1:
                    handleBuy(scanner);
                    break;
                case 2:
                    handleSell(scanner);
                    break;
                case 3:
                    handleViewStockInfo(scanner);
                    break;
                case 4:
                    handleViewPositions(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid input. Please enter a valid menu option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu Options: ");
        System.out.println("1: Buy");
        System.out.println("2: Sell");
        System.out.println("3: View stock info");
        System.out.println("4: View your positions");
        System.out.println("5: Exit\n");
        System.out.print("> ");
    }

    private int getUserInput(Scanner scanner) {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            scanner.next(); // Consume non-integer input
            return -1; // Invalid input
        }
    }

    private void handleBuy(Scanner scanner) {
        Optional<Quote> optional = handleViewStockInfo(scanner);

        if(optional.isPresent()) {
            Quote quote = optional.get();
            System.out.println("Please enter what you would like to do (buy/anything else to go back to menu):");
            String action = scanner.next();
            if(action.equals("buy")) {
                System.out.println("Please enter an amount:");
                int amount = scanner.nextInt();
                quoteService.saveQuote(quote);
                Integer id = positionService.buy(quote, amount);
                if(id == null) {
                    System.out.println("Failed to purchase");
                } else {
                    System.out.println("Successful purchase");
                }
            }

        }
    }
    private void handleSell(Scanner scanner) {
        handleViewPositions(scanner);
        System.out.println("Please enter the ID of the one you wish to sell: ");
        int id = scanner.nextInt();
        positionService.sell(id);
    }

    private void handleViewPositions(Scanner scanner) {
        System.out.println("Please enter a stock symbol:");
        String symbol = scanner.next();

        Iterable<Position> retrievedPositions = positionService.positionsBySymbol(symbol);
        List<Position> retrievedPositionList = new ArrayList<>();
        retrievedPositions.forEach(retrievedPositionList::add);

        System.out.println("These are all the Position for the symbol: " + symbol);

        for(Position position: retrievedPositionList) {
            System.out.println(position);
        }
    }


    private Optional<Quote> handleViewStockInfo(Scanner scanner) {
        System.out.println("Please enter a stock symbol.");
        String symbol = scanner.next();
        Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPI(symbol);
        if (quoteOptional.isEmpty()) {
            System.out.println("Couldn't find stock with symbol " + symbol + ".");
        } else {
            System.out.println(quoteOptional.get());
        }
        return quoteOptional;
    }


}
