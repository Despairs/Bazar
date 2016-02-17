package org.investsoft.bazar;

import android.test.InstrumentationTestCase;
import android.util.Log;

import org.investsoft.bazar.api.model.post.LoginRequest;
import org.investsoft.bazar.utils.HttpClient;
import org.investsoft.bazar.utils.JsonHelper;

/**
 * Created by Despairs on 15.01.16.
 */

public class HttpLoginTest extends InstrumentationTestCase {

    public void testJson() {
        LoginRequest req = new LoginRequest("user", "pass");
        String json = JsonHelper.toJson(req);
        Log.i("LOGIN_TEST", json);
        assertNotNull(json);
        HttpClient client = new HttpClient("http://api.shop.projects.investsoft.org/", "auth/login.php");
        String resp = client.sendPostRequest(json);
        Log.i("LOGIN_TEST", resp);
        assertNotNull(resp);

    }

}
