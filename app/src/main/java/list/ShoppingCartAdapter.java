package list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.r9_bl.bookshelf.R;
import java.util.ArrayList;

public class ShoppingCartAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> itemId;

    public ShoppingCartAdapter(Context context, ArrayList<String> itemId) {
        super(context, R.layout.list_shopping_cart, R.id.textView, itemId);
        this.context = context;
        this.itemId = itemId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_shopping_cart, parent, false);

        //TODO: Must be replace with logic using "bookId.get(position)" and obtain detail from database.

        return rowView;
    }

}