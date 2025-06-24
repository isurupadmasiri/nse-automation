package com.nse;

import com.nse.pages.HomePage;
import com.nse.pages.IndicesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IndicesPageTest extends BaseTest {
    public static final String INDICES_PAGE_TITLE = "Market Watch - Indices";

    @Test(description = "Verify that the Indices Page loads successfully")
    public void verifyIndicesPageLoadsSuccessfully() {
        IndicesPage indicesPage = page.getInstance(HomePage.class).goToIndicesPage();
        indicesPage.waitForIndicesPageLoad();
        Assert.assertEquals(indicesPage.getIndicesPageTitle(), INDICES_PAGE_TITLE, "Indices Page: Incorrect title");
    }
}
