package ifsc.ctds.syscontas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import ifsc.ctds.syscontas.entity.Contas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ContasEditController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private Label lblValor;

    @FXML
    private Label lblVencimento;
    
    @FXML
    private Label lblSituacao;

    @FXML
    private TextField txtValor;

    @FXML
    private TextField txtVencimento;
    
    @FXML
    private TextField txtSituacao;
    
    @FXML
    private MenuButton menuSituacao;
    
    @FXML
    private MenuItem menuPago;
    
    @FXML
    private MenuItem menuPendente;

    @FXML
    private HBox pnlBotoes;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnCancelar;
         
    //Objeto caixa
    private Contas conta;
    //flag da operação onclick
    private boolean okClick = false;
    //Referência a janela
    private Stage janelaContasEdit;
    
    public Stage getJanelaContasEdit() {
		return janelaContasEdit;
	}

	public void setJanelaContasEdit(Stage janelaContasEdit) {
		this.janelaContasEdit = janelaContasEdit;
	}
	
	//método para preencher na tela os valores de uma nova caixa
	public void populaTela(Contas contas) {
	    this.conta = contas;
	    this.txtValor.setText(String.format("%.2f", contas.getValor()));
	    this.txtVencimento.setText(contas.getVencimento());
	    this.menuSituacao.setText(contas.getSituacao()); // Atualiza o texto do MenuButton
	}
	
	private boolean validaCampos() {
		String mensagemErros = new String();
		//validar o campo cor - não pode ser branco
		if (this.txtValor.getText() == null || 
				this.txtValor.getText().trim().length() == 0) {
			mensagemErros += "Informe o valor!\n";
		}
		//validar o campo etiqueta - não pode ser branco
		if (this.txtVencimento.getText() == null || 
				this.txtVencimento.getText().trim().length() == 0) {
			mensagemErros += "Informe o vencimento!\n";
		}
		//Verifica se há mensagens de erro
		if (mensagemErros.length() == 0) {
			return true;
		}else {
	    	Alert alerta = new Alert(Alert.AlertType.ERROR);
	    	alerta.setTitle("Dados Inválidos");
	    	alerta.setHeaderText("Favor corrgir as seguintes informações");
	    	alerta.setContentText(mensagemErros);
	    	alerta.showAndWait();
	    	return false;			
		}
	}

	public boolean isOkClick() {
		return okClick;
	}
	
	@FXML
	void onSelectPago(ActionEvent event) {
	    this.menuSituacao.setText("Pago");
	    if (this.conta != null) {
	        this.conta.setSituacao("Pago");
	    }
	}

	@FXML
	void onSelectPendente(ActionEvent event) {
	    this.menuSituacao.setText("Pendente");
	    if (this.conta != null) {
	        this.conta.setSituacao("Pendente");
	    }
	}
		
	@FXML
    void onClickBtnCancelar(ActionEvent event) {
		this.getJanelaContasEdit().close();
	}

    @FXML
    void onClickBtnConfirmar(ActionEvent event) {
    	if (validaCampos()) {
    	    // Pega o valor digitado no TextField e converte para double
    	    double valor = Double.parseDouble(this.txtValor.getText());
    	    
    	    // Passa o valor direto como double para o método setValor
    	    this.conta.setValor(valor);
    	    
    	    // Pega o vencimento como está
    	    this.conta.setVencimento(this.txtVencimento.getText());
    	    
    	    // Sinaliza o fim da operação
    	    this.okClick = true;
    	    this.getJanelaContasEdit().close();
    	}

    }
  
        
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

}
