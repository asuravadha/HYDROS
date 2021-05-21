package in.countrybaskets.hydros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class product extends AppCompatActivity {
EditText e;
TextView t;
Button eb,rb;
FirebaseFirestore mstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        eb=findViewById(R.id.adddata);
        rb=findViewById(R.id.receivedata);
        e=findViewById(R.id.edata);
        t=findViewById(R.id.rdata);
        mstore=FirebaseFirestore.getInstance();
        //add data
       eb.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Create a new user with a first and last name
               Map<String, Object> user = new HashMap<>();
               user.put("data", ""+e.getText().toString());

// Add a new document with a generated ID
              mstore.collection("Usersdata")
                       .add(user)
                       .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                           @Override
                           public void onSuccess(DocumentReference documentReference) {
                               Toast.makeText(product.this, "success", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(product.this, "Failed", Toast.LENGTH_SHORT).show();
                           }
                       });
           }
       });

        //receive data
        rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
mstore.collection("Usersdata").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    @Override
    public void onComplete(@NonNull  Task<QuerySnapshot> task) {
        if(task.isSuccessful()){
            String fbdata="";
            for (QueryDocumentSnapshot document : task.getResult()) {
               fbdata+="\n"+document.getData();

            }
            t.setText(fbdata);
        }else{
            Toast.makeText(product.this, "could not read data", Toast.LENGTH_SHORT).show();
        }

    }
});
            }
        });
    }
}