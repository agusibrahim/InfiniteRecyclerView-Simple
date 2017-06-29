package ai.infrv;

import android.app.*;
import android.os.*;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.*;
import java.util.List;
import java.util.ArrayList;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;
import ai.infrv.MainActivity.*;
import android.widget.Toast;

// Agus Ibrahim
// http://fb.me/mynameisagoes

public class MainActivity extends Activity 
{
	RecyclerView recyclerView;
	List<Integer> data=new ArrayList<Integer>();
	private MainActivity.Adapter adapt;
	private LinearLayoutManager lmgr;
	private boolean isloadData=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		recyclerView=(RecyclerView) findViewById(R.id.mainList);
		for(int i=0;i<30;i++) data.add(i);
		recyclerView.setLayoutManager(lmgr=new LinearLayoutManager(this));
		recyclerView.setAdapter(adapt=new Adapter());
		
		registerRVLoadMore(recyclerView);
    }
	private void registerRVLoadMore(RecyclerView r){
		r.addOnScrollListener(new RecyclerView.OnScrollListener(){
				@Override
				public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy)  {
					int akhir=lmgr.findLastVisibleItemPosition();
					if(akhir+1==data.size()&&!isloadData){
						data.add(-1);
						adapt.notifyItemInserted(data.size()-1);
						loadData();
						Toast.makeText(MainActivity.this, "Load more...",0).show();
						isloadData=true;
					}
				}
			});
	}
	private void loadData(){
		// load data (virtual), 3 second delay
		new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					data.remove(data.size()-1);
					int last=data.get(data.size()-1);
					for(int j=1;j<20;j++){
						data.add(last+j);
					}
					adapt.notifyDataSetChanged();
					isloadData=false;
				}
			}, 3000);
	}
	private class Adapter extends RecyclerView.Adapter<Holdr> {
		@Override
		public MainActivity.Holdr onCreateViewHolder(ViewGroup p1, int p2) {
			return new Holdr(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null));
		}
		@Override
		public void onBindViewHolder(MainActivity.Holdr holdr, int pos) {
			holdr.txt.setText(data.get(pos)==-1?"> Loading...": "Item "+data.get(pos));
		}
		@Override
		public int getItemCount() {
			return data.size();
		}
	}
	private class Holdr extends RecyclerView.ViewHolder{
		public TextView txt;
		public Holdr(View v){
			super(v);
			txt=(TextView) v.findViewById(android.R.id.text1);
		}
	}
}
