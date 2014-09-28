package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.*;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.TestUtils.*;

public class SeparatorCommandsTest
{
   private JPanel panel = createMock(JPanel.class);
   private PanelCommand panelCommand;
   private Direction actualDirection;

   @Before
   public void setUp()
   {
      panel.setLayout((LayoutManager) anyObject());
      panelCommand = panel().on(panel).withComponentFactory(new ComponentFactory()
      {
         @Override
         public JComponent createSeparator(Direction direction)
         {
            actualDirection = direction;
            return super.createSeparator(direction);
         }
      });
   }

   @Test
   public void addSeparatorToHorizontalPanel()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.CENTER, AY.BOTH)));

      replay(panel);

      panelCommand
         .addSeparator()
         .getComponent();

      verify(panel);
      assertEquals(actualDirection, Direction.TOP_TO_BOTTOM);
   }

   @Test
   public void whenAddingSeparatorToHorizontalPanelWithGrowingComponentsThenSeparatorIsNotFilledHorizontally()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.CENTER, AY.BOTH).withWeight(1.0)));

      replay(panel);

      panelCommand
         .withDefault(specWithFill())
         .addSeparator()
         .getComponent();

      verify(panel);
   }

   @Test
   public void addSeparatorToVerticalPanel()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.BOTH, AY.CENTER)));

      replay(panel);

      panelCommand
         .withDirection(Direction.TOP_TO_BOTTOM)
         .addSeparator()
         .getComponent();

      verify(panel);
      assertEquals(actualDirection, Direction.LEFT_TO_RIGHT);
   }

   @Test
   public void whenAddingSeparatorToVerticalPanelWithGrowingComponentsThenSeparatorIsNotFilledVertically()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.BOTH, AY.CENTER).withWeight(1.0)));

      replay(panel);

      panelCommand
         .withDirection(Direction.TOP_TO_BOTTOM)
         .withDefault(specWithFill())
         .addSeparator()
         .getComponent();

      verify(panel);
   }

   @Test
   public void addLineSeparatorToHorizontalPanel()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.BOTH, AY.CENTER).withGridWidthRemainder()));

      replay(panel);

      panelCommand
         .addLineSeparator()
         .getComponent();

      verify(panel);
      assertEquals(actualDirection, Direction.LEFT_TO_RIGHT);
   }

   @Test
   public void whenAddingLineSeparatorToHorizontalPanelWithGrowingComponentsThenSeparatorIsNotFilledVertically()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.BOTH, AY.CENTER).withGridWidthRemainder().withWeight(1.0)));

      replay(panel);

      panelCommand
         .withDefault(specWithFill())
         .addLineSeparator()
         .getComponent();

      verify(panel);
   }

   @Test
   public void addLineSeparatorToVerticalPanel()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.CENTER, AY.BOTH).withGridHeightRemainder()));

      replay(panel);

      panelCommand
         .withDirection(Direction.TOP_TO_BOTTOM)
         .addLineSeparator()
         .getComponent();

      verify(panel);
      assertEquals(actualDirection, Direction.TOP_TO_BOTTOM);
   }

   @Test
   public void whenAddingLineSeparatorToVerticalPanelWithGrowingComponentsThenSeparatorIsNotFilledHorizontally()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.CENTER, AY.BOTH).withGridHeightRemainder().withWeight(1.0)));

      replay(panel);

      panelCommand
         .withDirection(Direction.TOP_TO_BOTTOM)
         .withDefault(specWithFill())
         .addLineSeparator()
         .getComponent();

      verify(panel);
   }

   @Test
   public void addingLineSeparatorToHorizontalPanelPutsSeparatorInSeparateLine()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec()));
      panel.add(anyObject(JSeparator.class), gbc(1, 0, defaultSpec().withInsetLeft(5)));
      panel.add(anyObject(JSeparator.class), gbc(0, 1, defaultSpec().withInsetTop(5).withAnchor(AX.BOTH, AY.CENTER).withGridWidthRemainder()));
      panel.add(anyObject(JSeparator.class), gbc(0, 2, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand
         .add(aComponent())
         .add(aComponent())
         .addLineSeparator()
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void addingLineSeparatorToVerticalPanelPutsSeparatorInSeparateLine()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec()));
      panel.add(anyObject(JSeparator.class), gbc(0, 1, defaultSpec().withInsetTop(5)));
      panel.add(anyObject(JSeparator.class), gbc(1, 0, defaultSpec().withInsetLeft(5).withAnchor(AX.CENTER, AY.BOTH).withGridHeightRemainder()));
      panel.add(anyObject(JSeparator.class), gbc(2, 0, defaultSpec().withInsetLeft(5)));

      replay(panel);

      panelCommand
         .withDirection(Direction.TOP_TO_BOTTOM)
         .add(aComponent())
         .add(aComponent())
         .addLineSeparator()
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingLineSeparatorWithSpecThisSpecOverridesDefaultHeaderSpec()
   {
      panel.add(anyObject(JSeparator.class), gbc(0, 0, defaultSpec().withAnchor(AX.LEFT, AY.CENTER).withGridWidthRemainder()));

      replay(panel);

      panelCommand
         .add(new LineSeparatorCommand().withSpec(spec().withAnchorX(AX.LEFT)))
         .getComponent();

      verify(panel);
   }
}
