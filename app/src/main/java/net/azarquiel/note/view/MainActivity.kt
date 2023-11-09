package net.azarquiel.note.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import net.azarquiel.listacompra.model.Product
import net.azarquiel.note.R
import net.azarquiel.recyclerviewproducts.adapter.ProductAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: NoteAdapter
    private lateinit var rvProducts: RecyclerView
    private var contador: Int=-1
    private lateinit var contadorSH: SharedPreferences
    private lateinit var listaSH: SharedPreferences

    private fun onClickFab() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login/Register")
        val ll = LinearLayout(this)
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val tilProductName = TextInputLayout(this)
        tilProductName.layoutParams = lp
        val etProductName = EditText(this)
        etProductName.setPadding(0, 80, 0, 80)
        etProductName.textSize = 20.0F
        etProductName.hint = "Product"
        tilProductName.addView(etProductName)

        val tilProductStock = TextInputLayout(this)
        tilProductStock.layoutParams = lp
        val etProductStock = EditText(this)
        etProductStock.setPadding(0, 80, 0, 80)
        etProductStock.textSize = 20.0F
        etProductStock.hint = "Stock"
        tilProductStock.addView(etProductStock)
        ll.addView(tilProductName)
        ll.addView(tilProductStock)
        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val product = Product(incrementaContador(), etProductName.text.toString(), etProductStock.text.toString())
            addProduct(product)
            adapter.setProducts(getLista())
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaSH = getSharedPreferences("listacompra", Context.MODE_PRIVATE)
        contadorSH = getSharedPreferences("contador", Context.MODE_PRIVATE)
        getContador()

        rvProducts = findViewById(R.id.rvProducts)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener({v -> onClickFab()})

        initRv()

        adapter.setProducts(getLista())
    }

    private fun initRv() {
        adapter = ProductAdapter(this, R.layout.row_product)
        rvProducts.adapter = adapter
        rvProducts.layoutManager = LinearLayoutManager(this)
    }

    private fun getContador() {
        contador = contadorSH.getInt("c", -1)
        if (contador==-1) contador = 0
    }
    private fun incrementaContador(): Int{
        contador++
        val edit = contadorSH.edit()
        edit.putInt("c", contador)
        edit.commit()
        return contador
    }
    private fun getLista(): List<Product> {
        val listaAll = listaSH.all
        val products = ArrayList<Product>()
        for ((key,value) in listaAll) {
            val jsonProducto = value.toString()
            val product = Gson().fromJson(jsonProducto, Product::class.java)
            products.add(product)
        }
        return products.toList()
    }

    private fun tostada(product: Product) {
        Toast.makeText(this, product.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun addProduct(product: Product) {
        val editor = listaSH.edit()
        val productojson = Gson().toJson(product)
        editor.putString(product.id.toString(), productojson)
        editor.commit()
    }

}