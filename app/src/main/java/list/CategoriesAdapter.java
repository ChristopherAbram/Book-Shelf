package list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.r9_bl.bookshelf.R;

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> categoryID;

    public CategoriesAdapter(Context context, ArrayList<String> categoryID) {
        super(context, R.layout.list_categories, R.id.textView, categoryID);
        this.context = context;
        this.categoryID = categoryID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_categories, parent, false);

        TextView categoryTextView = rowView.findViewById(R.id.category_text_view);

        //TODO: categoryTextView should be Title of the category instead of ID.
        categoryTextView.setText(categoryID.get(position));
        return rowView;
    }

}