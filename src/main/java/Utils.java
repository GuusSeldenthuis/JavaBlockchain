import java.security.MessageDigest;
import java.security.SecureRandom;

public class Utils
{
    /**
     * Applies Sha256 to a string and returns the result.
     *
     * @param input The data to be inputted.
     * @return A hashed {@link String} representing the data as SHA-256.
     */
    public static String applySHA256(String input)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));

            // ToDo: Check if moving 0's is needed/optimal for all hashing rounds.
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++)
            {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a SHA-256d hash based on the SHA-256 hash method.
     *
     * @param input The data to be inputted.
     * @return A hashed {@link String} representing the data as SHA-256d.
     */
    public static String applySHA256d(String input)
    {
        return applySHA256(applySHA256(input));
    }

    /**
     * Method for gestating a random string of data at a given length.
     *
     * @param len Length of the to-be-generated string.
     */
    public static char[] randomString(int len)
    {
        String AB = " .!@#$%^&*()_+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        char[] dataString = new char[len];
        for (int i = 0; i < len; i++)
        {
            dataString[i] = AB.charAt(rnd.nextInt(AB.length()));
        }
        return dataString;
    }


}
