package net.unesc.hal.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.unesc.hal.data.Source;
import net.unesc.hal.exceptions.EditorException;
import net.unesc.hal.models.Lexicon;
import net.unesc.hal.utils.File;
import net.unesc.hal.views.Editor;

public class EditorListener implements ActionListener {

    private Editor ed;

    public EditorListener(Editor ed) {
        this.ed = ed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        try {
            handle(cmd);
        } catch (EditorException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Arquivo inexistente!");
        }
    }

    private void handle(String cmd) throws EditorException, IOException {
        Source src = ed.getSource();
        
        if(src == null || src.getCode().isEmpty()) return;
        
        switch (cmd) {
            case Editor.SAVE:
                File.write("/", src.toString());
                break;
            case Editor.OPEN:
                File.read("/");
                break;
            case Editor.CLOSE:
                ed.dispose();
                break;
            case Editor.NEW:
                ed.setSource("");
                break;
            case Editor.LEXICON:
                Lexicon lex = new Lexicon(src, ed.getFiniteAutomaton());
                ed.setTokens(lex.getTokens());
                ed.setErrors(lex.getErrors());
                break;
            default:
                throw new EditorException("Comando inexistente: " + cmd);
        }

    }
}
