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
    public BigInteger dechiffreRsa_chinois(BigInteger c)
    {
        BigInteger d_mod_phip = d.mod(p.subtract(BigInteger.ONE));
        BigInteger d_mod_phiq = d.mod(q.subtract(BigInteger.ONE));
        BigInteger c_puis_d_mod_phip = c.modPow(d_mod_phip, n);
        BigInteger c_puis_d_mod_phiq = c.modPow(d_mod_phiq, n);
        BigInteger q_invp = q.modInverse(p);
        BigInteger p_invq = p.modInverse(q);
        BigInteger res1 = c_puis_d_mod_phip.multiply(q).multiply(q_invp);
        BigInteger res2 = c_puis_d_mod_phiq.multiply(p).multiply(p_invq);
        BigInteger res = res1.add(res2).mod(n);
        return res;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Rsa r = new Rsa(2048);
        System.out.println("cle public : ( "+r.getN()+", "+r.getE()+" )");
        System.out.println("cle privee : ( "+r.getD()+", "+r.getP()+", "+r.getQ()+" )");
        BigInteger m = new BigInteger("12345");
        BigInteger c = m.modPow(r.getE(), r.getN());
        System.out.println("message : "+m);
        System.out.println("chiffré : "+c);
        System.out.println("debut : "+ System.currentTimeMillis());
        BigInteger dc = c.modPow(r.getD(), r.getN());
        System.out.println("dechiffré : "+dc);
        System.out.println("fin method 1 : "+ System.currentTimeMillis());
        System.out.println("dechiffré (chinois): "+r.dechiffreRsa_chinois(c));
        System.out.println("fin method 2 : "+ System.currentTimeMillis());
    }
    
}

   
