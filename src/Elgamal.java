import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Elgamal {

  private final static int bitLength = 256;
  private static BigInteger one;
  private static SecureRandom random;
  private Charset charset = StandardCharsets.UTF_8;

  private BigInteger p;
  private BigInteger g;
  private BigInteger y;
  private BigInteger x;

  public Elgamal() {
    random = new SecureRandom();
    one = BigInteger.valueOf(1);
  }

  public BigInteger getP() {
    return p;
  }

  public BigInteger getG() {
    return g;
  }

  public BigInteger getY() {
    return y;
  }

  public BigInteger getX() {
    return x;
  }

  public void init() {
    p = BigInteger.probablePrime(bitLength, random);
    g = calculateG();
    x = calcaulateXK(p, false);
    y = g.modPow(x, p);
  }

  public String encrypt(String message, Object... key) {
    BigInteger p = (BigInteger) key[0];
    BigInteger g = (BigInteger) key[1];
    BigInteger y = (BigInteger) key[2];

    BigInteger encryptedMessage = new BigInteger(message.getBytes(charset));

    BigInteger k = calcaulateXK(p, true);
    BigInteger a = g.modPow(k, p);
    BigInteger b = (y.pow(k.intValue()).multiply(encryptedMessage)).mod(p);

    return a.toString() + " " + b.toString();
  }

  public String decrypt(String message, Object... key) {
    BigInteger p = (BigInteger) key[0];
    BigInteger x = (BigInteger) key[1];

    BigInteger degree = p.subtract(one).subtract(x);

    String[] values = message.split(" ");
    BigInteger a = new BigInteger(values[0]);
    BigInteger b = new BigInteger(values[1]);

    BigInteger decryptedMessage = /* (a.pow(degree.intValue()).multiply(b)).mod(p); */
        (b.divide(a.pow(x.intValue()))).mod(p);

    return new String(decryptedMessage.toByteArray(), charset);
  }

  private BigInteger calculateG() {
    BigInteger pMinusOne = p.subtract(one);

    for (BigInteger i = BigInteger.valueOf(3); i.compareTo(p) < 0; i = i.add(one)) {
      if ((i.modPow(pMinusOne, p)).equals(one)) {
        return i;
      }
    }
    return BigInteger.ZERO;
  }

  private BigInteger calcaulateXK(BigInteger p, boolean isK) {
    BigInteger pMinusOne = p.subtract(one);
    BigInteger initialValue = BigInteger.TWO;

    if (isK) {
      initialValue = x.add(one);
    }

    for (BigInteger i = initialValue; i.compareTo(pMinusOne) < 0; i = i.add(one)) {
      if (i.isProbablePrime(1) && i.gcd(pMinusOne).equals(one)) {
        return i;
      }
    }

    return BigInteger.valueOf(2);
  }
}