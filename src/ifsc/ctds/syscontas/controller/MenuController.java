package ifsc.ctds.syscontas.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ifsc.ctds.syscontas.dao.ContasDAO;
import ifsc.ctds.syscontas.entity.Contas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements Initializable{

	@FXML
    private TableView<Contas> tbvContas;

    @FXML
    private TableColumn<Contas, Integer> tbcCodigo;

    @FXML
    private TableColumn<Contas, Double> tbcValor;

    @FXML
    private AnchorPane pnlContas;

    @FXML
    private GridPane pnlDetalhes;

    @FXML
    private Label lblValor;

    @FXML
    private Label lblVencimento;

    @FXML
    private Label lblSituacao;

    @FXML
    private Label lblValorVal;

    @FXML
    private Label lblVencimentoVal;

    @FXML
    private Label lblSituacaoVal;

    @FXML
    private ButtonBar btnBar;

    @FXML
    private Button btnIncluir;

    @FXML
    private Tooltip tipIncluir;

    @FXML
    private Button btnEditar;

    @FXML
    private Tooltip tipEditar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Tooltip tipExcluir;

  //Lista de objetos caixa
    private List<Contas> listaContas;
    //Lista observável - atualiza a UI
    private ObservableList<Contas> observableListaContas = FXCollections.observableArrayList();
    //Objeto para acessar e realizar as operações no banco sobre a caixa
    private ContasDAO contasDAO;
    
    public static final String CONTAS_EDITAR = " - Editar";
    public static final String CONTAS_INCLUIR = " - Incluir";        
   
    public ContasDAO getContasDAO() {
		return contasDAO;
	}
    
	/**********************************************************************/
	/*********************GETTERS E SETTERS********************************/
	/**********************************************************************/

    public void setContasDAO(ContasDAO contasDAO) {
		this.contasDAO = contasDAO;
	}	
		
	public List<Contas> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<Contas> listaContas) {
		this.listaContas = listaContas;
	}

	public ObservableList<Contas> getObservableListaContas() {
		return observableListaContas;
	}

	public void setObservableListaContas(ObservableList<Contas> observableListaContas) {
		this.observableListaContas = observableListaContas;
	}

	/**********************************************************************/
	/***********************INICIALIZAÇÃO**********************************/
	/**********************************************************************/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Cria um objeto DAO para realizar as operações do banco de dados na tabela CAIXA
		this.setContasDAO(new ContasDAO());
		//Configurações iniciais da TableView e carregamento de dados do banco de dados
		this.carregarTableViewContas();		
		//Deixe em branco a seleção de um item da tabela (painel de detalhes fica em branco)
		this.selecionarItemTableViewContas(null);
		//adiciona um evento ao selecionar um item da tabela (atualiza o painel de detalhes)
		this.tbvContas.getSelectionModel().selectedItemProperty().addListener(
				(Observable, oldValue, newValue) -> selecionarItemTableViewContas(newValue)		
		);
		//preenche a tabela com os dados salvos na lista (fxcollections)
		this.tbvContas.setItems(getObservableListaContas());
	}
	
	public void carregarTableViewContas() {
		//configuração das colunas da tabela com a propriedade adequada
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("idConta"));
		this.tbcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		//leitura da lista de caixa do banco de dados
		this.setListaContas(this.getContasDAO().getAll());
		//cria um objeto observable para monitorar atualizaçãoes na lista de caixa
		this.setObservableListaContas(FXCollections.observableArrayList(this.getListaContas()));	
		//vincula o objeto observable a tabela de caixas
		//necessário para fazer atualização automáticas da tabela após operações no banco de dados
		this.tbvContas.setItems(this.getObservableListaContas());
	}
	
	public void selecionarItemTableViewContas(Contas contas) {
		//se existe uma caixa selecionada
		if (contas != null) {
			//atualiza a UI com os valores da caixa
			this.lblValorVal.setText(String.format("%.2f", contas.getValor()));
			this.lblVencimentoVal.setText(contas.getVencimento());	
			this.lblSituacaoVal.setText(contas.getSituacao());

		}
		//senão existe caixa selecionada
		else {
			//atualiza a UI com os valores em branco
			this.lblValorVal.setText("");
			this.lblVencimentoVal.setText("");
			this.lblSituacaoVal.setText("");
		}		
	}
	
	public boolean onShowTelaContasEditar(Contas contas, String operacao) {
	    try {
	        FXMLLoader loader = new FXMLLoader(
	            getClass().getResource("/ifsc/ctds/syscontas/view/ContasEdit.fxml"));
	        Parent contasEditXML = loader.load();
	        Stage janelaContasEditar = new Stage();
	        janelaContasEditar.setTitle("Cadastro de Conta" + operacao);
	        janelaContasEditar.initModality(Modality.APPLICATION_MODAL);
	        janelaContasEditar.resizableProperty().setValue(Boolean.FALSE);
	        Scene contasEditLayout = new Scene(contasEditXML);
	        janelaContasEditar.setScene(contasEditLayout);
	        ContasEditController contasEditController = loader.getController();
	        contasEditController.setJanelaContasEdit(janelaContasEditar);
	        contasEditController.populaTela(contas);
	        janelaContasEditar.showAndWait();
	        return contasEditController.isOkClick();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }        
	    return false;        
	}
	
	/**********************************************************************/
	/***********************CONFIGURAÇÃO DOS BOTÕES************************/
	/**********************************************************************/	
    @FXML
    void onClickBtnIncluir(ActionEvent event) {
    	System.out.println("1");
    	Contas contas = new Contas();
    	System.out.println("2");
    	boolean btnConfirmarClick = this.onShowTelaContasEditar(contas, 
    			MenuController.CONTAS_INCLUIR);
    	System.out.println("3");
    	if (btnConfirmarClick) {
    		System.out.println("4");
    		this.getContasDAO().create(contas);
    		System.out.println("5");
    		this.carregarTableViewContas();
    		System.out.println("6");
    	}
    }
    
    @FXML
    void onClickBtnEditar(ActionEvent event) {   
    	Contas contas = (Contas) this.tbvContas.getSelectionModel().getSelectedItem();
    	//Se existe caixa selecionada procede a operação
    	if (contas != null) {
    		boolean btnConfirmarClick = this.onShowTelaContasEditar(contas, 
    				MenuController.CONTAS_EDITAR);    		
    		if (btnConfirmarClick) {
    			this.getContasDAO().update(contas, null);
    			this.carregarTableViewContas();
    		}
    	//caso não haja caixa selecionada mostra um alerta de erro
    	} else {
    		Alert alerta = new Alert(Alert.AlertType.ERROR);
    		alerta.setContentText("Por favor, escolha uma caixa na tabela!");
    		alerta.show();
    	}
    }
    @FXML
    void onClickBtnExcluir(ActionEvent event) {
    	Contas contas = (Contas) this.tbvContas.getSelectionModel().getSelectedItem();    	
    	if (contas != null) {
    		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        	alerta.setTitle("Confirmar exclusão");
        	alerta.setHeaderText("Deseja realmente excluir a conta no valor de R$ " + contas.getValor()+"?");
        	ButtonType botaoNao = ButtonType.NO;
        	ButtonType botaoSim = ButtonType.YES;
        	alerta.getButtonTypes().setAll(botaoSim,botaoNao);
        	//pegar a resposta do usuário
        	Optional<ButtonType> resultado = alerta.showAndWait();
        	if (resultado.get() == botaoSim) {
        		this.getContasDAO().delete(contas);
        		this.carregarTableViewContas();
        	}
    	}
    	else {
    		Alert alerta = new Alert(Alert.AlertType.ERROR);
    		alerta.setContentText("Por favor, escolha uma conta na tabela!");
    		alerta.show();
    	}
    }


	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Confirmar saída");
		alerta.setHeaderText("Deseja sair do sistema?");
		ButtonType botaoNao = ButtonType.NO;
		ButtonType botaoSim = ButtonType.YES;
		alerta.getButtonTypes().setAll(botaoSim,botaoNao);
		//pegar a resposta do usuário
		Optional<ButtonType> resultado = alerta.showAndWait();
		return resultado.get() == botaoSim ? true : false;
	}
    
}

