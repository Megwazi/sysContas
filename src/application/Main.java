package application;
	
import ifsc.ctds.syscontas.controller.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//carregando arquivo de layout
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/ifsc/ctds/syscontas/view/Menu.fxml"));
			Parent menuXML = loader.load();
			//carregando o controller
			MenuController menuController = loader.getController();
			//cria cena e a janela principal
			Scene menuLayout = new Scene(menuXML);
			Stage menuJanela = new Stage();
			menuJanela.setTitle("Menu do Sistema");
			menuJanela.initModality(Modality.APPLICATION_MODAL);
			menuJanela.resizableProperty().setValue(Boolean.FALSE);
			menuJanela.setScene(menuLayout);
			menuJanela.show();			
			//posicionar a janela no meio da tela do computador
			Rectangle2D posicaoJanela = Screen.getPrimary().getVisualBounds();
			menuJanela.setX((posicaoJanela.getWidth() - menuJanela.getWidth())/2);
			menuJanela.setY((posicaoJanela.getHeight() - menuJanela.getHeight())/2);			
			//configurar o evento de fechar a janela
			menuJanela.setOnCloseRequest(
					event -> { 
						if (menuController.onCloseQuery())
							System.exit(0);
						else
							event.consume();
					}
			); 		
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
