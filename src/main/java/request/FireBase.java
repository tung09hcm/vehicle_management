package request;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class FireBase {

    private FireBase() throws Exception {
        byte[] key1 = {0x45, 0x47, 0x57, 0x47, 0x74, 0x51, 0x4f, 0x51, 0x4d, 0x73, 0x70, 0x55, 0x73, 0x76, 0x5a, 0x6c, 0x4a, 0x50, 0x4b, 0x5a, 0x7a, 0x71, 0x36, 0x42, 0x59, 0x37, 0x35, 0x4c, 0x69, 0x6a, 0x45, 0x48};
        byte[] key2 = {0x4e, 0x48, 0x43, 0x72, 0x77, 0x7a, 0x6e, 0x61, 0x6b, 0x45, 0x76, 0x63, 0x5a, 0x76, 0x6c, 0x70, 0x35, 0x31, 0x4d, 0x59, 0x45, 0x38, 0x7a, 0x31, 0x54, 0x7a, 0x39, 0x38, 0x6f, 0x32, 0x45, 0x6d};
        byte[] token = readFromFile("src/main/java/request/token");
        byte[] decrypted = decrypt(token, key1, key2);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(decrypted));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setDatabaseUrl("https://vehicle-manager-hcmut-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();

        FirebaseApp.initializeApp(options);
    }
    public byte[] decrypt(byte[] data, byte[] key1, byte[] key2) throws Exception {
        byte[] decryptedWithKey1 = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            decryptedWithKey1[i] = (byte) (data[i] ^ key1[i % key1.length]);
        }
        SecretKeySpec keySpec = new SecretKeySpec(key2, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedWithAES = cipher.doFinal(Base64.getDecoder().decode(new String(decryptedWithKey1)));

        return decryptedWithAES;
    }

    public byte[] readFromFile(String fileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileName);
        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);
        inputStream.close();
        return data;
    }

//    public static void main(String[] args) throws Exception {
//        FireBase fb = new FireBase();
//
////        CompletableFuture<Void> future = fb.getVoidCompletableFuture();
////        future.join();
//        System.out.println(fb.getVoidCompletableFuture());
//        System.out.println("Done");
//    }


    private String getVoidCompletableFuture() {
        final String[] data = new String[1];
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        CompletableFuture<Void> future = new CompletableFuture<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data[0] = dataSnapshot.getValue().toString();
                future.complete(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                future.completeExceptionally(databaseError.toException());
            }
        });
        future.join();
        return data[0];
    }
}



