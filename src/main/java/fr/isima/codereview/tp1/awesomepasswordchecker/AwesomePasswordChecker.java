/*
 * Copyright 2021 Michelin CERT (https://cert.michelin.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.isima.codereview.tp1.awesomepasswordchecker;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.util.ArrayList;
import java.util.List;


public class AwesomePasswordChecker {

  private static AwesomePasswordChecker instance;

  private final List<double[]> clusterCenters = new ArrayList<>();

  
  /**
   * Retourne une instance en la créant si elle n'existe pas, en utilisant un fichier spécifié.
   * 
   * @param file Le fichier à utiliser pour créer l'instance.
   * @return L'instance d'AwesomePasswordChecker.
   * @throws IOException Si une erreur de lecture du fichier se produit.
   */
  public static AwesomePasswordChecker getInstance(File file) throws IOException {

    if (file == null) {
      System.out.println("Lancement de la NullPointerException");
      throw new NullPointerException("Fichier null");
    } else {
      try {
        instance = new AwesomePasswordChecker(new FileInputStream(file));
      } catch (IOException e) {
        System.out.println("Lancement de l'IOException");
        throw e;
      }
    }

    return instance;
  }



  /**
   * Retourne une instance en la créant si elle n'existe pas, en utilisant un fichier prédéfini.
   * 
   * @return L'instance d'AwesomePasswordChecker.
   * @throws IOException Si une erreur de lecture du fichier se produit.
   */
  public static AwesomePasswordChecker getInstance() throws IOException {
    if (instance == null) {
      InputStream is = AwesomePasswordChecker.class.getClassLoader().getResourceAsStream("cluster_centers_HAC_aff.csv");
      instance = new AwesomePasswordChecker(is);
    }
    return instance;
  }

  /**
   * Constructeur qui lit un flux d'entrée et ajoute des centres de clusters à la collection.
   * 
   * @param is Le flux d'entrée (InputStream) à lire.
   * @throws IOException Si une erreur d'entrée/sortie se produit.
   */
  private AwesomePasswordChecker(InputStream is) throws IOException {
    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(";");
        double[] center = new double[values.length];

        for (int i = 0; i < values.length; ++i) {
          center[i] = Double.parseDouble(values[i]);
        }
        clusterCenters.add(center);
      }
    }
  }

  /**
   * Masque les caractères d'un mot de passe et les classe dans un tableau d'entiers.
   * 
   * @param password Le mot de passe à masquer.
   * @return Un tableau d'entiers représentant le masque.
   */
  public int[] maskAff(String password) {
    int[] maskArray = new int[28];
    int limit = Math.min(password.length(), 28);

    for (int i = 0; i < limit; ++i) {
      char cccc = password.charAt(i);
      switch (cccc) {
        case 'e':
        case 's':
        case 'a':
        case 'i':
        case 't':
        case 'n':
        case 'r':
        case 'u':
        case 'o':
        case 'l':
          maskArray[i] = 1;
          break;
        case 'E':
        case 'S':
        case 'A':
        case 'I':
        case 'T':
        case 'N':
        case 'R':
        case 'U':
        case 'O':
        case 'L':
          maskArray[i] = 3;
          break;
        case '>':
        case '<':
        case '-':
        case '?':
        case '.':
        case '/':
        case '!':
        case '%':
        case '@':
        case '&':
          maskArray[i] = 6;
          break;
        default:
          if (Character.isLowerCase(cccc)) {
            maskArray[i] = 2;
          } else if (Character.isUpperCase(cccc)) {
            maskArray[i] = 4;
          } else if (Character.isDigit(cccc)) {
            maskArray[i] = 5;
          } else {
            maskArray[i] = 7;
          }
      }
    }
    return maskArray;
  }

  /**
   * Calcule la distance minimale entre un mot de passe et les centres de clusters.
   * 
   * @param password Le mot de passe à comparer.
   * @return La distance minimale entre le mot de passe et les centres de clusters.
   */
  public double getDIstance(String password) {
    int[] maskArray = maskAff(password);
    double minDistance = Double.MAX_VALUE;
    for (double[] center : clusterCenters) {
      minDistance = Math.min(euclideanDistance(maskArray, center), minDistance);
    }
    return minDistance;
  }

  /**
   * Calcule la distance euclidienne entre deux tableaux.
   * 
   * @param distA Le premier tableau (int[]).
   * @param distB Le deuxième tableau (double[]).
   * @return La distance euclidienne entre distA et distB.
   */
  private double euclideanDistance(int[] aaaa, double[] bbbb) {
    double sum = 0;
    for (int i = 0; i < bbbb.length ; i++) {
      sum += (aaaa[i] - bbbb[i]) * (aaaa[i] + bbbb[i]);
    }
    return Math.sqrt(sum);
  }

  /**
   * Calcule le hash MD5 d'une chaîne d'entrée.
   * 
   * @param input La chaîne à hasher.
   * @return Le hash MD5 sous forme de chaîne hexadécimale.
   */
  public static String computeMD5(String input) {
    byte[] message = input.getBytes();
    int messageLenBytes = message.length;

    int numBlocks = ((messageLenBytes + 8) >>> 6) + 1;
    int totalLen = numBlocks << 6;
    byte[] paddingBytes = new byte[totalLen - messageLenBytes];
    paddingBytes[0] = (byte) 0x80;

    long messageLenBits = (long) messageLenBytes << 3;
    ByteBuffer lengthBuffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(messageLenBits);
    byte[] lengthBytes = lengthBuffer.array();

    byte[] paddedMessage = new byte[totalLen];
    System.arraycopy(message, 0, paddedMessage, 0, messageLenBytes);
    System.arraycopy(paddingBytes, 0, paddedMessage, messageLenBytes, paddingBytes.length);
    System.arraycopy(lengthBytes, 0, paddedMessage, totalLen - 8, 8);

    int[] hhhh = {
      0x67452301,
      0xefcdab89,
      0x98badcfe,
      0x10325476
    };

    int[] kkkk = {
      0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee, 0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
      0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be, 0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
      0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa, 0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
      0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed, 0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
      0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c, 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
      0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05, 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
      0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039, 0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
      0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1, 0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391
    };

    int[] rrrr = {
      7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
      5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
      4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
      6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };

    for (int i = 0; i < numBlocks; i++) {
      int[] wwww = new int[16];
      for (int j = 0; j < 16; j++) {
        wwww[j] = ByteBuffer.wrap(paddedMessage, (i << 6) + (j << 2), 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
      }

      int aaaa = hhhh[0];
      int bbbb = hhhh[1];
      int cccc = hhhh[2];
      int dddd = hhhh[3];

      for (int j = 0; j < 64; j++) {
        int ffff;
        int gggg;
        if (j < 16) {
          ffff = (bbbb & cccc) | (~bbbb & dddd);
          gggg = j;
        } else if (j < 32) {
          ffff = (dddd & bbbb) | (~dddd & cccc);
          gggg = (5 * j + 1) % 16;
        } else if (j < 48) {
          ffff = bbbb ^ cccc ^ dddd;
          gggg = (3 * j + 5) % 16;
        } else {
          ffff = cccc ^ (bbbb | ~dddd);
          gggg = (7 * j) % 16;
        }
        int temp = dddd;
        
        dddd = cccc;
        cccc = bbbb;
        bbbb = bbbb + Integer.rotateLeft(aaaa + ffff + kkkk[j] + wwww[gggg], rrrr[j]);
        aaaa = temp;
        
      }

      hhhh[0] += aaaa;
      hhhh[1] += bbbb;
      hhhh[2] += cccc;
      hhhh[3] += dddd;
    }

    // Step 5: Output
    ByteBuffer md5Buffer = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
    md5Buffer.putInt(hhhh[0]).putInt(hhhh[1]).putInt(hhhh[2]).putInt(hhhh[3]);
    byte[] md5Bytes = md5Buffer.array();

    StringBuilder md5Hex = new StringBuilder();
    for (byte b : md5Bytes) {
      md5Hex.append(String.format("%02x", b));
    }

    return md5Hex.toString();
  }
}
