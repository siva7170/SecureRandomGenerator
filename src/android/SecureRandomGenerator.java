package tn.cgs.securerandom;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
/**
 * This class echoes a string called from JavaScript.
 */
public class SecureRandomGenerator extends CordovaPlugin {
    private SecureRandom secureRandom;
    private int noOfLen;
    private String typeGenerator; // RAND_NUMBER, RAND_ALPHA, RAND_ALPHANUMERIC, RAND_ALPHANUMERICSYMBOL

    private String randNumber="0123456789";
    private String randAlpha="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijiklmnopqrstuvwxyz";
    private String randAlphanumericsymbol="/!@_-+$#*";

    public SecureRandomGenerator(){
        secureRandom=new SecureRandom();
    }
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("generateSecureRandom")){
            noOfLen = Integer.parseInt(args.getString(0));
            typeGenerator = args.getString(1);

            if(noOfLen<1){
                callbackContext.error("Please enter valid length of random generator");
            }
            else{
                String tempGenSecure=GeneratorSecureRandom(noOfLen, typeGenerator);
                if (tempGenSecure.length() > 0) {
                    callbackContext.success(tempGenSecure);
                } else {
                    callbackContext.error("Could not generator secure random.");
                }
                return true;
            }
        }
        return false;
    }

    private String GeneratorSecureRandom(int length, String typeOfGenerator){
        String tempTypeOfGen="";
        char[] bufStr=new char[length];
        switch(typeOfGenerator){
            case "RAND_ALPHA":
                tempTypeOfGen=randAlpha;
                break;
            case "RAND_ALPHANUMERIC":
                tempTypeOfGen=randNumber+""+randAlpha;
                break;
            case "RAND_ALPHANUMERICSYMBOL":
                tempTypeOfGen=randNumber+""+randAlpha+""+randAlphanumericsymbol;
                break;
            default:
                tempTypeOfGen=randNumber;
        }

        for(int i=0;i<length;i++){
            bufStr[i]=tempTypeOfGen.charAt(secureRandom.nextInt(tempTypeOfGen.length()));
        }

        return new String(bufStr);
    }
}