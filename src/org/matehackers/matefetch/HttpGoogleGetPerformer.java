package org.matehackers.matefetch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.widget.TextView;

/**
 * 
 */
public class HttpGoogleGetPerformer extends AsyncTask<Void, Void, String>{

	private static final String GOOGLE_URL = "http://www.google.com";
	private static final int HTTP_RESPONSE_OK = 200;
	private TextView mTextView;

	/**
	 * Passa o objeto que precisaremos atualizar quando a tarefa for concluída
	 */
	public HttpGoogleGetPerformer(TextView textView) {
		mTextView = textView;
	}

	/**
	 * Vai no site do google e recupera o código Html.
	 */
	private String GetGoogleHtmlCode() throws ClientProtocolException, IOException, URISyntaxException {
		// objeto que faz o papel de 'navegador'
		HttpClient client = new DefaultHttpClient();
		
		// identificador do recurso que queremos baixar (Universal Resource Identifier)
		URI website = new URI(GOOGLE_URL);
		
		// o metodo que usaremos para acessar o servidor
		HttpGet get = new HttpGet();
		
		// seta o identificador do metodo para a URI criada
		get.setURI(website);
		
		// armazena a resposta do servidor. bloqueia até finalizar
		HttpResponse response = client.execute(get);
		
		// esperamos um 200, qualquer outra coisa é erro
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HTTP_RESPONSE_OK) {
			
			// pega uma referencia para a area de memoria onde a resposta está armazenada
			InputStream stream = response.getEntity().getContent();
			
			// constrói um leitor para esta área de memória
			InputStreamReader regularReader = new InputStreamReader(stream);
			
			// transforma o leitor em um leitor bufferizado, o que possibilita a leitura em lote.
			BufferedReader bufferedReader = new BufferedReader(regularReader);
			
			// retorna a primeira linha da resposta
			return bufferedReader.readLine();
			
		} else {
			// nos informa o que deu errado
			return "error" + String.valueOf(statusCode);
		}
	}

	/**
	 * Estabelece qual a operação em background que será executado
	 */
	@Override
	protected String doInBackground(Void... params) {
		try {
			return GetGoogleHtmlCode();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "erro";
	}
	
	/**
	 * Atualiza a interface com a informação
	 */
	@Override
	protected void onPostExecute(String result) {
		mTextView.setText(result);
	}

}
