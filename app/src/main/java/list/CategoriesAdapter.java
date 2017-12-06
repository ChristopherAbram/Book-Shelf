package list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.r9_bl.bookshelf.R;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> catId;

    public CategoriesAdapter(Context context, ArrayList<String> catId) {
        super(context, R.layout.list_categories, R.id.textView, catId);
        this.context = context;
        this.catId = catId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_categories, parent, false);

        //TODO: Must be replace with logic using "bookId.get(position)" and obtain detail from database.

        return rowView;
    }

}