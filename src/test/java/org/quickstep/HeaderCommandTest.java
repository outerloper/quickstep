package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.*;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.TestUtils.aComponent;
import static org.quickstep.TestUtils.defaultSpec;
import static org.quickstep.TestUtils.gbc;

public class HeaderCommandTest
{
   private JPanel panel = createMock(JPanel.class);
   private PanelCommand panelCommand;
   private boolean headerInFirstRowHandled = false;

   @Before
   public void setUp()
   {
      panel.setLayout((LayoutManager) anyObject());
      panelCommand = panel().
         with(panel).
         withComponentFactory(new ComponentFactory()
         {
            @Override
            public JComponent createHeader(String title, boolean first)
            {
               headerInFirstRowHandled = first;
               return super.createHeader(title, first);
            }
         });
   }

   @Test
   public void whenAddingHeaderToHorizontalPanelThenItIsAddedInSeparateLine()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec()));
      panel.add(anyObject(JSeparator.class), gbc(1, 0, defaultSpec().withInsetLeft(5)));
      panel.add(anyObject(JSeparator.class), gbc(0, 1, defaultSpec().withInsetTop(5).withAnchorX(AX.BOTH).withGridWidthRemainder()));
      panel.add(anyObject(JSeparator.class), gbc(0, 2, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         add(aComponent()).
         add(aComponent()).
         addHeader("header").
         add(aComponent()).
         getComponent();

      verify(panel);
      assertFalse(headerInFirstRowHandled);
   }

   @Test
   public void whenAddingHeaderToVerticalPanelItIsNotAddedInSeparateLine()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec()));
      panel.add(anyObject(JSeparator.class), gbc(0, 1, defaultSpec().withInsetTop(5)));
      panel.add(anyObject(JSeparator.class), gbc(0, 2, defaultSpec().withInsetTop(5).withAnchorX(AX.BOTH)));
      panel.add(anyObject(JSeparator.class), gbc(0, 3, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         withOrientation(Orientation.VERTICAL).
         add(aComponent()).
         add(aComponent()).
         addHeader("header").
         add(aComponent()).
         getComponent();

      verify(panel);
      assertFalse(headerInFirstRowHandled);
   }

   @Test
   public void whenAddingHeaderAsFirstElementItIsHandledInSpecialWay()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchorX(AX.BOTH).withGridWidthRemainder()));
      panel.add(anyObject(JSeparator.class), gbc(0, 1, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         addHeader("header").
         add(aComponent()).
         getComponent();

      verify(panel);
      assertTrue(headerInFirstRowHandled);
   }

   @Test
   public void givenVerticalPanelWhenAddingHeaderAnywhereInFirstRowItIsHandledInSpecialWay()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec()));
      panel.add(anyObject(JSeparator.class), gbc(0, 1, defaultSpec().withInsetTop(5)));
      panel.add(anyObject(JSeparator.class), gbc(1, 0, defaultSpec().withInsetLeft(5).withAnchorX(AX.BOTH)));
      panel.add(anyObject(JSeparator.class), gbc(1, 1, defaultSpec().withGap(5)));

      replay(panel);

      panelCommand.
         withOrientation(Orientation.VERTICAL).
         withLineLength(2).
         add(aComponent()).
         add(aComponent()).
         addHeader("header").
         add(aComponent()).
         getComponent();

      verify(panel);
      assertTrue(headerInFirstRowHandled);
   }

   @Test
   public void whenAddingHeaderWithSpecThisSpecOverridesDefaultHeaderSpec()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchorX(AX.LEFT).withGridWidthRemainder()));

      replay(panel);

      panelCommand.
         add(new HeaderCommand("header").withSpec(spec().withAnchorX(AX.LEFT))).
         getComponent();

      verify(panel);
   }
}
