package view;

import org.junit.Test;
import model.player.Currency;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by c15aen on 2016-12-05.
 */
public class MenuTest {

        String[] test = {"a", "b", "c"};
        ActionListener a = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
        };

        @Test
        public void shouldCreateButton() {
                Menu m = new Menu(test, "test", (a));

                JMenuItem mi = (JMenuItem) m.returnButtons().get(0);
                JMenuItem mi2 = m.createButton("a");
                assertEquals(mi, mi2);

                }
        }

