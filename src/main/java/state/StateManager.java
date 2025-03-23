package state;

import state.concrete.*;

public class StateManager {
    private State currState;
    private CreateElementState createElementState;
    private CreateBondState createBondState;
    private DeleteState deleteState;
    private MoveState moveState;
    private AddAttributeState addAttributeState;
    private MultiselectState multiselectState;
    private DuplicateState duplicateState;
    private ZoomInState zoomInState;
    private ZoomOutState zoomOutState;
    private MoveOnScreenState moveOnScreenState;

    public StateManager(){
        initStates();
    }
    private void initStates(){
        createBondState = new CreateBondState();
        createElementState = new CreateElementState();
        deleteState = new DeleteState();
        moveState = new MoveState();
        addAttributeState = new AddAttributeState();
        multiselectState = new MultiselectState();
        duplicateState = new DuplicateState();
        zoomInState = new ZoomInState();
        zoomOutState = new ZoomOutState();
        moveOnScreenState = new MoveOnScreenState();

        currState = moveState; //cisto da se stavi na pocetku da je neki state init
    }
    public State getCurrState(){return currState;}
    public void setCreateElementState(){currState = createElementState;}
    public void setCreateBondState(){currState = createBondState;}
    public void setDeleteState(){currState = deleteState;}
    public void setMoveState(){currState = moveState;}
    public void setAddAttributeState(){currState = addAttributeState;}
    public void setMultiselectState(){currState = multiselectState;}
    public void setDuplicateState(){currState = duplicateState;}
    public void setZoomInState(){currState = zoomInState;}
    public void setZoomOutState(){currState = zoomOutState;}
    public void setMoveOnScreenState(){currState = moveOnScreenState;}
}
