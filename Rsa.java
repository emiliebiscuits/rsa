/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Emilie
 */
public class Rsa {
    private BigInteger p,q,n,e,d;
    private int k;
    private BigInteger Phi;
    public Rsa(int param)
    {
        k = param;
        p = randomGene(k);
        q = randomGene(k);
        n = p.multiply(q);
        Phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = geneE(Phi);
        d = e.modInverse(Phi);
    }

    private BigInteger randomGene(int numBits)
    {
       BigInteger res;
        do{
            res = new BigInteger(numBits/2, new Random());
        }while(!res.isProbablePrime(100) || res.bitLength()!=numBits/2);
        return res;
    }
    private BigInteger geneE(BigInteger N)
    {
        BigInteger res;
        do{
            res = new BigInteger(N.subtract(BigInteger.ONE).bitLength(), new Random());
        }while(res.gcd(N).compareTo(BigInteger.ONE)!=0 || res.compareTo(BigInteger.ONE)<1 );
        return res;
    }
       //modInverse(BigInteger m)
    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Rsa r = new Rsa(30);
        System.out.println("cle public : ( "+r.getN()+", "+r.getE()+" )");
        System.out.println("cle privee : ( "+r.getD()+", "+r.getP()+", "+r.getQ()+" )");
        BigInteger m = new BigInteger("123");
        BigInteger c = m.modPow(r.getE(), r.getN());
        System.out.println("message : "+m);
        System.out.println("chiffré : "+c);
        BigInteger dc = c.modPow(r.getD(), r.getN());
        System.out.println("dechiffré : "+dc);
    }
    
}
