package com.example.lab5_dialog__notification;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtId, edtName, edtCategory, edtPrice;
    Button btnAdd, btnRead, btnUpdate, btnDelete;
    ListView listViewProducts;

    DatabaseHandler db;
    List<Product> productList;
    ArrayAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtCategory = findViewById(R.id.edtCategory);
        edtPrice = findViewById(R.id.edtPrice);

        btnAdd = findViewById(R.id.btnAdd);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        listViewProducts = findViewById(R.id.listViewProducts);

        db = new DatabaseHandler(this);
        productList = new ArrayList<>();

        // Tải dữ liệu lần đầu
        loadProducts();

        // 1. NÚT THÊM
        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();
            String price = edtPrice.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Tên và Giá không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            db.addProduct(new Product(name, category, price));
            Toast.makeText(this, "Đã thêm hàng mới", Toast.LENGTH_SHORT).show();
            clearFields();
            loadProducts();
        });

        // 2. NÚT ĐỌC
        btnRead.setOnClickListener(v -> {
            loadProducts();
            Toast.makeText(this, "Đã tải lại danh sách kho", Toast.LENGTH_SHORT).show();
        });

        // 3. NÚT SỬA
        btnUpdate.setOnClickListener(v -> {
            String idStr = edtId.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Mã SP để sửa!", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = Integer.parseInt(idStr);
            String name = edtName.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();
            String price = edtPrice.getText().toString().trim();

            db.updateProduct(new Product(id, name, category, price));
            Toast.makeText(this, "Đã cập nhật mã SP " + id, Toast.LENGTH_SHORT).show();
            clearFields();
            loadProducts();
        });

        // 4. NÚT XÓA
        btnDelete.setOnClickListener(v -> {
            String idStr = edtId.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Mã SP để xóa!", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = Integer.parseInt(idStr);
            db.deleteProduct(id);
            Toast.makeText(this, "Đã xóa mã SP " + id, Toast.LENGTH_SHORT).show();
            clearFields();
            loadProducts();
        });

        // Sự kiện: Bấm vào 1 dòng -> Đẩy dữ liệu lên 4 ô nhập
        listViewProducts.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = productList.get(position);
            edtId.setText(String.valueOf(selectedProduct.getId()));
            edtName.setText(selectedProduct.getName());
            edtCategory.setText(selectedProduct.getCategory());
            edtPrice.setText(selectedProduct.getPrice());
        });
    }

    private void loadProducts() {
        productList.clear();
        productList.addAll(db.getAllProducts());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        listViewProducts.setAdapter(adapter);
    }

    private void clearFields() {
        edtId.setText("");
        edtName.setText("");
        edtCategory.setText("");
        edtPrice.setText("");
    }
}