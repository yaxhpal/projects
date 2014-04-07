package es.oeuvr.util;

import java.security.*;

public class Hash
{
	private static String digestAlgorithm = "SHA-256";
	private static String charset = "UTF-8";
    private MessageDigest digest;

    public Object clone()
        throws CloneNotSupportedException
    {
        return new Hash((MessageDigest) (digest.clone()));
    }

    protected Hash(MessageDigest d)
    {
        digest = d;
    }

    public Hash()
        throws Exception
    {
        digest = MessageDigest.getInstance(digestAlgorithm);
    }

    public void extract(int[] digest, int offset)
    {
        throw new UnsupportedOperationException();
    }

    public void update(byte b)
    {
        digest.update(b);
    }

    public void update(byte[] data, int offset, int length)
    {
        digest.update(data, offset, length);
    }

    public void update(byte[] data)
    {
        digest.update(data);
    }

    public byte[] digest()
    {
        return digest.digest();
    }

    public void digest(boolean reset, byte[] buffer, int offset)
    {
        if (reset != true)
            throw new UnsupportedOperationException();

        try
        {
            digest.digest(buffer, offset, digest.getDigestLength());
        }
        catch (DigestException e)
        {
            throw new IllegalStateException(e.toString());
        }
    }

    public int digestSize()
    {
        return 8 * 20;
    }

    private static final String bytesToHex(byte[] bs)
    {
        StringBuffer sb = new StringBuffer(bs.length * 2);

        for (int i = 0; i != bs.length; i++)
        {
            sb.append(Character.forDigit((bs[i] >>> 4) & 0xf, 16));
            sb.append(Character.forDigit(bs[i] & 0xf, 16));
        }

        return sb.toString();
    }

    public static final String hash(String s)
    {
        try
        {
            Hash sha = new Hash();

            sha.update(s.getBytes(charset));

            return bytesToHex(sha.digest());
        }
        catch (Exception uex)
        {
            // Ignore
        }

        return null;
    }

    public static void main(String[] args)
    {
        // for (int i = 0; i != 10000000; i++) hash(args[0]);

        System.out.println(hash(args[0]));
    }
}
