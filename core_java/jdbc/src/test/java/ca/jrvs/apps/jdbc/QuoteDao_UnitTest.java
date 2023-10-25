package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.helper.ApiManager;
import org.junit.After;
import org.junit.Before;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class QuoteDao_UnitTest {

    private MockedStatic<ApiManager> mockedApiManager;

    @Before
    public void setup() {
        mockedApiManager = Mockito.mockStatic(ApiManager.class);

        mockedApiManager.when(() -> ApiManager.fetchQuoteInfo(ArgumentMatchers.anyString())).thenReturn(TestHelper.newQuoteDTO());
    }

    @After
    public void tearDown() {
        mockedApiManager.close();
    }

}
