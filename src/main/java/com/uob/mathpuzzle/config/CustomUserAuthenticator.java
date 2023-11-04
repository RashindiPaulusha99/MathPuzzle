package com.uob.mathpuzzle.config;

import com.uob.mathpuzzle.exception.MathException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.uob.mathpuzzle.constant.ApplicationConstant.FORBIDDEN_RESOURCE;
import static org.springframework.security.oauth2.common.exceptions.OAuth2Exception.INVALID_TOKEN;

@Log4j2
@Service
public class CustomUserAuthenticator {

    public static void checkPublicUserIdWithToken(long id, String jwtToken) {
        try {
            log.info("\nChecking id with token: {}", id);
            if (id != getUserFromToken(jwtToken).getLong("id"))
                throw new MathException(403, FORBIDDEN_RESOURCE);
            log.info("\nuser id matches with id: {}", id);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("\nUnauthorized token: {}\n", e.getMessage());
            throw new MathException(403, FORBIDDEN_RESOURCE);
        }
    }

    public static String getEmailFromToken(String jwtToken) {

        return getUserFromToken(jwtToken).getString("email");
    }

    public static String getAdminIdFromToken(String jwtToken) {

        return getUserFromToken(jwtToken).getString("id");
    }

    public static String getServiceFromToken(String jwtToken) {
        JSONObject tokenJson = getJsonObjectFromJwt(jwtToken);
        return tokenJson.getJSONObject("service").get("name").toString();
    }

    public static Date getLastLogOutTimeFromToken(String jwtToken) {
        try {
            JSONObject tokenJson = getJsonObjectFromJwt(jwtToken);
            return new Date(tokenJson.getLong("lastLogOutTime"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new MathException(401, INVALID_TOKEN);
        }
    }

    public static String getUserRoleFromToken(String jwtToken) {
        JSONObject tokenJson = getJsonObjectFromJwt(jwtToken);
        return tokenJson.getJSONArray("authorities").get(0).toString();
    }

    public static JSONObject getUserFromToken(String jwtToken) {
        JSONObject tokenJson = getJsonObjectFromJwt(jwtToken);
        return tokenJson;
    }

    public static JSONObject getJsonObjectFromJwt(String jwtToken) {
        try {
            log.debug("\nGet JSON from JWT : ");

            if (jwtToken.startsWith("Bearer")) {
                jwtToken = jwtToken.split(" ")[1];
            }
            /* ~~~~~~~~~~~ Decode JWT ~~~~~~~~~~~*/
            String[] split_string = jwtToken.split("\\.");
            String base64EncodedHeader = split_string[0];
            String base64EncodedBody = split_string[1];

            /*~~~~~~~~~ JWT Header ~~~~~~~*/
            Base64 base64Url = new Base64(true);

            /* ~~~~~~~~~ JWT Body ~~~~~~~~~*/
            String body = new String(base64Url.decode(base64EncodedBody));

            return new JSONObject(body);
        } catch (IndexOutOfBoundsException | IllegalArgumentException |
                 IllegalStateException | JSONException | NullPointerException n) {
            /*token is invalid or user is not found if hits here.*/
            log.error("Failed to get JSON from JWT: {}\tError: {} ", jwtToken, n);
            throw new MathException(400, INVALID_TOKEN);
        }
    }

}
