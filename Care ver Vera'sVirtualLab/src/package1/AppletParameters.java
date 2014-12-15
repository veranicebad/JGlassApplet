package package1;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Роман Воропаев
 */
public class AppletParameters {

    private static String decodeString(String val) throws GeneralSecurityException, IllegalArgumentException {
        if (val==null || val.isEmpty()) {
            throw new IllegalArgumentException("Empty parameter!");
        }
        String cipherName = "Blowfish/ECB/NoPadding";
        String key = "ilovebarsic";
        try {
            SecretKeySpec dks = new SecretKeySpec(key.getBytes("US-ASCII"),"Blowfish");        
            Cipher cipher = Cipher.getInstance(cipherName);
            cipher.init(Cipher.DECRYPT_MODE, dks);

            byte[] input = DatatypeConverter.parseBase64Binary(val);
            byte[] output = removePadding(cipher.doFinal(input));
            return new String(output,"US-ASCII");
        } catch (IOException e) {
            // Can't happen
            throw new GeneralSecurityException(e);
        }
    }
    
    /**
     * Достать и расшифровать строку с параметрами.
     * @param EncryptedParam зашифрованная строка
     * @return Хэштаблица значений параметров
     */
    public static Map<String, String> decode(String encryptedParams) 
            throws IllegalArgumentException, GeneralSecurityException {
        Map<String, String> map = new HashMap();
        try {            
            if (encryptedParams == null) {
                throw new IllegalArgumentException("Parameters aren't found! Check parameters name.");
            }            
            String paramsLine = AppletParameters.decodeString(encryptedParams.trim());                        
            String[] splittedLine = paramsLine.split(" ");
            if (splittedLine.length == 0) {
                throw new IllegalArgumentException("Parameters are empty!");
            }    
            for (String pair : splittedLine) {
                String[] splittedPair = pair.split("=");                
                if (splittedPair.length < 2) {
                    throw new IllegalArgumentException("Parameter is not in right format:" + pair);
                }
                map.put(splittedPair[0], splittedPair[1]);
            }
        } catch (GeneralSecurityException ex) {
            throw new IllegalArgumentException("Parameters given to applet are broken!");
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Parameter is not number!");
        }
        return map;
    }
        
    private static byte[] removePadding(byte[] buffer) {
        int end;
        for (end=0; buffer[end]>0 && end<buffer.length; end++);
        return Arrays.copyOf(buffer, end);
    }

    // Util static class
    private AppletParameters() {
    }
}
