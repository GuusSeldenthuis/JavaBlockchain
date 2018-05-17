import java.security.MessageDigest;

public class HashUtils
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
     * Moves all zeros within the given string to the front.
     *
     * @param oldHash Old/original hash.
     * @return Old hash with all zeros moved to the front.
     */
    public static String moveZerosToFront(String oldHash)
    {
        System.out.println(oldHash);

        StringBuilder newHash = new StringBuilder();
        for (int i = 0; i != 64; i++)
        {
            newHash.insert(oldHash.charAt(i) == '0' ? 0 : i, oldHash.charAt(i));
        }
        return newHash.toString();
    }


}
