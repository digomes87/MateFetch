package org.matehackers.matefetch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Um fragmento básico criado por padrão;
 */
public class SimpleTextFragment extends Fragment {

	TextView mTextView;
	
	/**
	 * Executado quando o fragmento é criado
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		// pega uma referência pro item de interface
		mTextView = (TextView) rootView.findViewById(R.id.trechinhoDeTexto);
		
		// instancia uma nova tarefa assíncrona
		HttpGoogleGetPerformer get = new HttpGoogleGetPerformer(mTextView);
		
		// executa a tarefa
		get.execute();
		
		// retorna a view
		return rootView;
	}
	
}