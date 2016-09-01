package pos.fa7.cursoweb.managedbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.commandbutton.CommandButton;

import com.sun.faces.component.visit.FullVisitContext;

import pos.fa7.cursoweb.util.MessageHelper;

@ViewScoped
@ManagedBean(name = "tictactoeBean")
public class TictactoeBean implements Serializable {

	private static final long serialVersionUID = 6006996087173694589L;

	private static final String TOKEN_VALUE = "-";
	private static final int BOARD_SIZE = 3;
	private static final String[] BUTTON_COMPONENTS = {"btn1", "btn2", "btn3","btn4","btn5","btn6","btn7","btn8","btn9"};

	private enum State {
		Blank, X, O
	};

	private State[][] board;
	private int moveCount;
	private boolean canMove;
	private State currentPlayer;

	private String playerNameO;
	private String playerNameX;
	private String mensagem;

	public void init(ActionEvent event) {
		currentPlayer = State.X;
		mensagem = MessageHelper.getMessage("page.tictactoe.label.current", getPlayerName());
		moveCount = 0;
		canMove = true;
		initBoard();
		clearButtons();
	}

	private UIComponent findComponent(final String id) {

	    FacesContext context = FacesContext.getCurrentInstance(); 
	    UIViewRoot root = context.getViewRoot();
	    final UIComponent[] found = new UIComponent[1];

	    root.visitTree(new FullVisitContext(context), new VisitCallback() {     
	        @Override
	        public VisitResult visit(VisitContext context, UIComponent component) {
	            if(component.getId().equals(id)){
	                found[0] = component;
	                return VisitResult.COMPLETE;
	            }
	            return VisitResult.ACCEPT;              
	        }
	    });
	    return found[0];
	}	
	
	private void clearButtons() {
		for (String componentName : BUTTON_COMPONENTS) {
			CommandButton cmdBtn = (CommandButton) findComponent(componentName);
			cmdBtn.setValue("");
			cmdBtn.setDisabled(false);
		}
	}

	private void initBoard() {
		board = new State[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = State.Blank;
			}
		}
	}

	private void changePlayer() {
		if (currentPlayer.equals(State.X)) {
			currentPlayer = State.O;
		} else {
			currentPlayer = State.X;
		}
	}

	public void move(ActionEvent event) {
		/* code updated from 
		 * http://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
		 */
		
		if (!canMove) {
			return;
		}

		String position = (String) event.getComponent().getAttributes().get("position");
		String[] values = (position).split(TOKEN_VALUE);
		int x = Integer.valueOf(values[0]);
		int y = Integer.valueOf(values[1]);

		if (board[x][y] == State.Blank) {
			board[x][y] = currentPlayer;
			CommandButton commandButton =((CommandButton) event.getComponent());
			commandButton.setValue(currentPlayer);
			commandButton.setDisabled(true);
		}

		moveCount++;

		// check end conditions

		// check col
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[x][i] != currentPlayer)
				break;
			if (i == BOARD_SIZE - 1) {
				mensagem = MessageHelper.getMessage("page.tictactoe.label.winner", getPlayerName());
				canMove = false;
				return;
			}
		}

		// check row
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[i][y] != currentPlayer)
				break;
			if (i == BOARD_SIZE - 1) {
				mensagem = MessageHelper.getMessage("page.tictactoe.label.winner", getPlayerName());
				canMove = false;
				return;
			}
		}

		// check diag
		if (x == y) {
			// we're on a diagonal
			for (int i = 0; i < BOARD_SIZE; i++) {
				if (board[i][i] != currentPlayer)
					break;
				if (i == BOARD_SIZE - 1) {
					mensagem = MessageHelper.getMessage("page.tictactoe.label.winner", getPlayerName());
					canMove = false;
					return;
				}
			}
		}

		// check anti diag
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (board[i][(BOARD_SIZE - 1) - i] != currentPlayer)
				break;
			if (i == BOARD_SIZE - 1) {
				mensagem = MessageHelper.getMessage("page.tictactoe.label.winner", getPlayerName());
				canMove = false;
				return;
			}
		}

		// check draw
		if (moveCount == (BOARD_SIZE * BOARD_SIZE)) {
			mensagem = MessageHelper.getMessage("page.tictactoe.label.draw");
			canMove = false;
			return;
		}

		changePlayer();
		mensagem = MessageHelper.getMessage("page.tictactoe.label.current", getPlayerName());
	}

	private String getPlayerName() {
		String playerName = StringUtils.EMPTY;
		if (currentPlayer.equals(State.O)) {
			playerName = playerNameO;
		} else {
			playerName = playerNameX;
		}
		return playerName;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getPlayerNameO() {
		return playerNameO;
	}

	public void setPlayerNameO(String playerNameO) {
		this.playerNameO = playerNameO;
	}

	public String getPlayerNameX() {
		return playerNameX;
	}

	public void setPlayerNameX(String playerNameX) {
		this.playerNameX = playerNameX;
	}

}