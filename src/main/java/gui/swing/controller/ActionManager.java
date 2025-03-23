package gui.swing.controller;

import gui.swing.controller.rightActions.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ActionManager {
    //izlist svih akcija
    private ExitAction exitAction;
    private NewAction newAction;
    private InfoAction infoAction;
    private DeleteAction deleteAction;
    private ChangeAuthorAction changeAuthorAction;
    private CreateElementAction createElementAction;
    private DeleteElementAction deleteElementAction;
    private CreateBondAction createBondAction;
    private MoveElementAction moveElementAction;
    private AddAttributeAction addAttributeAction;
    private MultiselectAction multiselectAction;
    private DuplicateAction duplicateAction;
    private ZoomInAction zoomInAction;
    private ZoomOutAction zoomOutAction;
    private MoveOnScreenAction moveOnScreenAction;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private SaveAction saveAction;
    private LoadAction loadAction;
    private SaveAsAction saveAsAction;
    private SaveTemplateAction saveTemplateAction;
    private LoadTemplateAction loadTemplateAction;
    private ExportAction exportAction;

    public ActionManager(){
        initActionManager();
    }

    private void initActionManager(){
        exitAction = new ExitAction();
        newAction = new NewAction();
        infoAction = new InfoAction();
        deleteAction = new DeleteAction();
        changeAuthorAction = new ChangeAuthorAction();
        createElementAction = new CreateElementAction();
        deleteElementAction = new DeleteElementAction();
        createBondAction = new CreateBondAction();
        moveElementAction = new MoveElementAction();
        addAttributeAction = new AddAttributeAction();
        multiselectAction = new MultiselectAction();
        duplicateAction = new DuplicateAction();
        zoomInAction = new ZoomInAction();
        zoomOutAction = new ZoomOutAction();
        moveOnScreenAction = new MoveOnScreenAction();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        saveAction = new SaveAction();
        loadAction = new LoadAction();
        saveAsAction = new SaveAsAction();
        saveTemplateAction = new SaveTemplateAction();
        loadTemplateAction = new LoadTemplateAction();
        exportAction = new ExportAction();
    }

}
