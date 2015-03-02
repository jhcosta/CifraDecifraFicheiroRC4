/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cifradecifrarc4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.exit;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException; 
import javax.crypto.Cipher; 
import javax.crypto.IllegalBlockSizeException; 
import javax.crypto.KeyGenerator; 
import javax.crypto.NoSuchPaddingException; 
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author jhcuser
 */
public class CifraDecifraRC4 {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, IOException {
       
        if(args.length < 2) {
            System.out.println("Usar:");
            System.out.println("-genkey <keyfile>");
            System.out.println("-enc <keyfile> <infile> <outfile>");
            System.out.println("-dec <keyfile> <infile> <outfile>");
            exit(0);
        }
        
        int i;
        
        for(i=1; i<args.length; i++) {
            StringBuilder sb = new StringBuilder("C:\\Users\\jhcuser\\Dropbox\\MEI\\SSI\\aula1\\");
            sb.append(args[i]);
            args[i] = sb.toString();
        }
       
        FileOutputStream outputStream;
        FileInputStream inputStream;
        SecretKey key;
        Cipher cifra;
        byte[] inBytes, outBytes;
        
        switch(args[0]) {
            case "-genkey": {
                // Cria instância do gerador de chaves
                KeyGenerator kg = KeyGenerator.getInstance("RC4");
                // Inicializa gerador de chaves (128bit) e gera chave
                kg.init(128);
                key = kg.generateKey();

                outputStream = new FileOutputStream(new File(args[1]));
                outputStream.write(key.getEncoded());
                outputStream.close();
                break;
            }
            case "-enc": {
                File keyFile = new File(args[1]);
                inputStream = new FileInputStream(keyFile);
		byte[] keyBytes = new byte[(int) keyFile.length()];
		inputStream.read(keyBytes);
                key = new SecretKeySpec(keyBytes,"RC4");
                cifra = Cipher.getInstance("RC4");
                cifra.init(Cipher.ENCRYPT_MODE,key);
                File inFile = new File(args[2]);
                inputStream = new FileInputStream(inFile);
		inBytes = new byte[(int) inFile.length()];
		inputStream.read(inBytes);
                inputStream.close();
                outBytes = cifra.doFinal(inBytes);
                outputStream = new FileOutputStream(new File(args[3]));
                outputStream.write(outBytes);
                outputStream.close();
                break;
            }
            case "-dec": {
                File keyFile = new File(args[1]);
                inputStream = new FileInputStream(keyFile);
		byte[] keyBytes = new byte[(int) keyFile.length()];
		inputStream.read(keyBytes);
                key = new SecretKeySpec(keyBytes,"RC4");
                cifra = Cipher.getInstance("RC4");
                cifra.init(Cipher.DECRYPT_MODE,key);
                File inFile = new File(args[2]);
                inputStream = new FileInputStream(inFile);
		inBytes = new byte[(int) inFile.length()];
		inputStream.read(inBytes);
                inputStream.close();
                outBytes = cifra.doFinal(inBytes);
                outputStream = new FileOutputStream(new File(args[3]));
                outputStream.write(outBytes);
                outputStream.close();
                break;
            }
            default: {
                System.out.println("Argumento invalido. Usar: -genkey, -enc ou -dec");
                break;
            }
        }
    }   
}
