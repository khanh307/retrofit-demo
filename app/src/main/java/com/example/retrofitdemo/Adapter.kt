package com.example.retrofitdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class Adapter(var context: Context, var array: ArrayList<Line>): BaseAdapter() {
    private class ViewHolder(view: View){
        var imageView: ImageView
        var textView: TextView

        init {
            imageView = view.findViewById(R.id.imageview)
            textView = view.findViewById(R.id.textview)
        }
    }


    override fun getCount(): Int {
        return array.size
    }

    override fun getItem(position: Int): Any {
        return array.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View?
        var viewHolder : ViewHolder
        if(convertView == null){
            var layoutFlater : LayoutInflater = LayoutInflater.from(context)
            view = layoutFlater.inflate(R.layout.layout_line, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView;
            viewHolder = convertView.tag as ViewHolder
        }
        var line : Line = getItem(position) as Line
        viewHolder.imageView.setImageBitmap(line.image)
        viewHolder.textView.text = line.name

        return view as View
    }
}