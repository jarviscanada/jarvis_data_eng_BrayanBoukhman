package ca.jrvs.apps.trading.model;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
/*
 * https://cloud.iexapis.com/stable/stock/{symbol}/quote
 * Model was generated from JSON data via https://www.jsonschema2pojo.org/
 */
@Generated("jsonschema2pojo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IexQuote {

    @JsonProperty("avgTotalVolume")
    private Object avgTotalVolume;
    @JsonProperty("calculationPrice")
    private Object calculationPrice;
    @JsonProperty("change")
    private Object change;
    @JsonProperty("changePercent")
    private Object changePercent;
    @JsonProperty("close")
    private Object close;
    @JsonProperty("closeSource")
    private Object closeSource;
    @JsonProperty("closeTime")
    private Object closeTime;
    @JsonProperty("companyName")
    private Object companyName;
    @JsonProperty("currency")
    private Object currency;
    @JsonProperty("delayedPrice")
    private Object delayedPrice;
    @JsonProperty("delayedPriceTime")
    private Object delayedPriceTime;
    @JsonProperty("extendedChange")
    private Object extendedChange;
    @JsonProperty("extendedChangePercent")
    private Object extendedChangePercent;
    @JsonProperty("extendedPrice")
    private Object extendedPrice;
    @JsonProperty("extendedPriceTime")
    private Object extendedPriceTime;
    @JsonProperty("high")
    private Object high;
    @JsonProperty("highSource")
    private Object highSource;
    @JsonProperty("highTime")
    private Object highTime;
    @JsonProperty("iexAskPrice")
    private Object iexAskPrice;
    @JsonProperty("iexAskSize")
    private Object iexAskSize;
    @JsonProperty("iexBidPrice")
    private Object iexBidPrice;
    @JsonProperty("iexBidSize")
    private Object iexBidSize;
    @JsonProperty("iexClose")
    private Object iexClose;
    @JsonProperty("iexCloseTime")
    private Object iexCloseTime;
    @JsonProperty("iexLastUpdated")
    private Object iexLastUpdated;
    @JsonProperty("iexMarketPercent")
    private Object iexMarketPercent;
    @JsonProperty("iexOpen")
    private Object iexOpen;
    @JsonProperty("iexOpenTime")
    private Object iexOpenTime;
    @JsonProperty("iexRealtimePrice")
    private Object iexRealtimePrice;
    @JsonProperty("iexRealtimeSize")
    private Object iexRealtimeSize;
    @JsonProperty("iexVolume")
    private Object iexVolume;
    @JsonProperty("lastTradeTime")
    private Object lastTradeTime;
    @JsonProperty("latestPrice")
    private Object latestPrice;
    @JsonProperty("latestSource")
    private Object latestSource;
    @JsonProperty("latestTime")
    private Object latestTime;
    @JsonProperty("latestUpdate")
    private Object latestUpdate;
    @JsonProperty("latestVolume")
    private Object latestVolume;
    @JsonProperty("low")
    private Object low;
    @JsonProperty("lowSource")
    private Object lowSource;
    @JsonProperty("lowTime")
    private Object lowTime;
    @JsonProperty("marketCap")
    private Object marketCap;
    @JsonProperty("oddLotDelayedPrice")
    private Object oddLotDelayedPrice;
    @JsonProperty("oddLotDelayedPriceTime")
    private Object oddLotDelayedPriceTime;
    @JsonProperty("open")
    private Object open;
    @JsonProperty("openTime")
    private Object openTime;
    @JsonProperty("openSource")
    private Object openSource;
    @JsonProperty("peRatio")
    private Object peRatio;
    @JsonProperty("previousClose")
    private Object previousClose;
    @JsonProperty("previousVolume")
    private Object previousVolume;
    @JsonProperty("primaryExchange")
    private Object primaryExchange;
    @JsonProperty("symbol")
    private Object symbol;
    @JsonProperty("volume")
    private Object volume;
    @JsonProperty("week52High")
    private Object week52High;
    @JsonProperty("week52Low")
    private Object week52Low;
    @JsonProperty("ytdChange")
    private Object ytdChange;
    @JsonProperty("isUSMarketOpen")
    private Object isUSMarketOpen;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}