package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.TestUtils.*;

public class SeparatorCommandsTest // TODO ability to provide custom separator: separator().on(new JSeparator) and then add JSeparator's orientation asserts to those tests
{
   private JPanel panel = createMock(JPanel.class);
   private PanelCommand panelCommand;

   @Before
   public void setUp()
   {
      debug();
      panel.setLayout((LayoutManager) anyObject());
      panelCommand = panel().with(panel);
   }

   @Test
   public void addSeparatorToHorizontalPanel()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.CENTER, AY.BOTH)));

      replay(panel);

      panelCommand.
         addSeparator().
         getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingSeparatorToHorizontalPanelWithGrowingComponentsThenSeparatorIsNotFilledHorizontally()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.CENTER, AY.BOTH).withWeight(1.0)));

      replay(panel);

      panelCommand.
         specifyDefault(specWithFill()).
         addSeparator().
         getComponent();

      verify(panel);
   }

   @Test
   public void addSeparatorToVerticalPanel()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.BOTH, AY.CENTER)));

      replay(panel);

      panelCommand.
         withOrientation(Orientation.VERTICAL).
         addSeparator().
         getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingSeparatorToVerticalPanelWithGrowingComponentsThenSeparatorIsNotFilledVertically()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.BOTH, AY.CENTER).withWeight(1.0)));

      replay(panel);

      panelCommand.
         withOrientation(Orientation.VERTICAL).
         specifyDefault(specWithFill()).
         addSeparator().
         getComponent();

      verify(panel);
   }

   @Test
   public void addLineSeparatorToHorizontalPanel()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.BOTH, AY.CENTER).withGridWidthRemaining()));

      replay(panel);

      panelCommand.
         addLineSeparator().
         getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingLineSeparatorToHorizontalPanelWithGrowingComponentsThenSeparatorIsNotFilledVertically()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.CENTER, AY.BOTH).withWeight(1.0)));

      replay(panel);

      panelCommand.
         specifyDefault(specWithFill()).
         addSeparator().
         getComponent();

      verify(panel);
   }

   @Test
   public void addLineSeparatorToVerticalPanel()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.CENTER, AY.BOTH).withGridHeightRemaining()));

      replay(panel);

      panelCommand.
         withOrientation(Orientation.VERTICAL).
         addLineSeparator().
         getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingLineSeparatorToVerticalPanelWithGrowingComponentsThenSeparatorIsNotFilledHorizontally()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.BOTH, AY.CENTER).withWeight(1.0)));

      replay(panel);

      panelCommand.
         withOrientation(Orientation.VERTICAL).
         specifyDefault(specWithFill()).
         addSeparator().
         getComponent();

      verify(panel);
   }
}
