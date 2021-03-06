package org.quickstep;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

import org.junit.Before;
import org.junit.Test;
import org.quickstep.command.PanelCommand;

import static org.easymock.EasyMock.*;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.TestUtils.*;

public class HorizontalGridBagBuilderTest
{
   private JPanel panel;
   private PanelCommand panelCommand;

   @Before
   public void setUp()
   {
      panel = createMock(JPanel.class);
      panel.setLayout((LayoutManager) anyObject());
      panelCommand = panel().on(panel);
   }

   @Test
   public void testAddComponents()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInsetLeft(5)));
      panel.add(anyComponent(), gbc(2, 0, defaultSpec().withInsetLeft(5)));

      replay(panel);

      panelCommand
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void givenLineLengthSetWhenAddingMoreComponentsThanLineLengthThenWrapLine()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInset(5, 0, 0, 0)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void whenLineBreakAddedThenAddFollowingComponentsInNewLine()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));

      replay(panel);

      panelCommand
         .add(aComponent())
         .addLineBreak()
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void whenGridWidthRemainderUsedThenAddFollowingComponentsInNewLine()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridWidthRemainder()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));

      replay(panel);

      panelCommand
         .add(aComponent(), spec().withGridWidthRemainder())
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void specifyCellDefaultsMethodOverridesImplicitDefaultSpecForAddedComponents()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInset(0, 0, 3, 10)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 10, 3, 10).withGridWidthRemainder()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(3, 0, 3, 10)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(3, 10, 3, 10)));

      replay(panel);

      panelCommand
         .withDefault(spec().withInset(10, 3))
         .add(aComponent())
         .add(aComponent(), spec().withGridWidthRemainder())
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void columnSpecOverridesCellDefaults()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withAnchorX(AX.RIGHT).withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withAnchorX(AX.RIGHT).withInset(5, 5, 0, 0)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .withColumn(1, spec().withAnchorX(AX.RIGHT))
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void rowSpecOverridesCellDefaults()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(20, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(20, 5, 0, 0)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .withRow(1, spec().withInsetTop(20))
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void cellSpecOverridesRowAndColumnSpec()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInset(10, 10, 0, 0)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .withCell(0, 0, spec().withInsetLeft(10).withInsetTop(10))
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void whenRowAndLaterColumnIsSpecifiedThenColumnSpecIsMoreImportant()
   {
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0).withInsetBottom(30)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .withRow(1, spec().withInsetBottom(20))
         .withColumn(1, spec().withInsetBottom(30))
         .addBlank().addBlank().addBlank()
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void whenColumnAndLaterRowIsSpecifiedThenRowSpecIsMoreImportant()
   {
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), anyGbc());
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0).withInsetBottom(30)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .withColumn(1, spec().withInsetBottom(20))
         .withRow(1, spec().withInsetBottom(30))
         .addBlank().addBlank().addBlank()
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void specProvidedForAddOverridesAllOtherSpecsForCurrentCell()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withAnchor(AX.LEFT, AY.TOP)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .withCell(0, 0, spec().withAnchor(AX.RIGHT, AY.BOTTOM))
         .add(aComponent(), spec().withAnchor(AX.LEFT, AY.TOP))
         .getComponent();

      verify(panel);
   }

   @Test
   public void componentsArePlacedOnGridRespectingTheirGridWidth()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridWidth(2)));
      panel.add(anyComponent(), gbc(2, 0, defaultSpec().withInset(0, 5, 0, 0)));

      replay(panel);

      panelCommand
         .add(aComponent(), spec().withGridWidth(2))
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void componentsArePlacedOnGridRespectingTheirGridHeight()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridHeight(2)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .add(aComponent(), spec().withGridHeight(2))
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void componentsArePlacedOnGridRespectingTheirGridSize()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridSize(2, 2)));
      panel.add(anyComponent(), gbc(2, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(2, 1, defaultSpec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInset(5, 0, 0, 0)));

      replay(panel);

      panelCommand
         .withLineLength(3)
         .add(aComponent(), spec().withGridSize(2, 2))
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void noMoreComponentsArePlacedAtColumnWithComponentWithGridHeightRemainder()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0).withGridHeightRemainder()));
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(0, 3, defaultSpec().withInset(5, 0, 0, 0)));

      replay(panel);

      panelCommand
         .withLineLength(2)
         .add(aComponent())
         .add(aComponent())
         .add(aComponent())
         .add(aComponent(), spec().withGridHeightRemainder())
         .add(aComponent())
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void applyStyleToEachComponentInCollectionAddedToBuilder()
   {
      java.util.List<JCheckBox> checkBoxes = new LinkedList<JCheckBox>();
      for (int i = 0; i < 6; i++)
      {
         checkBoxes.add(new JCheckBox());
      }
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInset(20, 30, 0, 0)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(20, 5, 0, 0)));
      panel.add(anyComponent(), gbc(2, 0, defaultSpec().withInset(20, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(20, 30, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(20, 5, 0, 0)));
      panel.add(anyComponent(), gbc(2, 1, defaultSpec().withInset(20, 5, 0, 0)));

      replay(panel);

      panelCommand
         .withLineLength(3)
         .withColumn(0, spec().withInsetLeft(30))
         .add(checkBoxes, spec().withInsetTop(20))
         .getComponent();

      verify(panel);
   }

   @Test
   public void addingBuilderUsesSpecOfThisBuilderToPlaceItsPanel()
   {
      JPanel auxPanel = new JPanel();

      panel.add(eq(auxPanel), gbc(0, 0, specWithFill().withInset(0, 0, 0, 50)));

      replay(panel);

      PanelCommand builder = panel().on(auxPanel)
         .withSpec(specWithFill());
      panelCommand
         .withCell(0, 0, spec().withInsetRight(50))
         .add(builder)
         .getComponent();

      verify(panel);
   }

   @Test
   public void addLineWithOneElementWithGridWidthRemainderAndThenAnotherLine()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridWidthRemainder()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand
         .add(line().add(aComponent(), spec().withGridWidthRemainder()))
         .add(line().add(aComponent()))
         .getComponent();

      verify(panel);
   }

   @Test
   public void addingSeq()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withIPad(4)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInsetLeft(5).withGridSize(2, 2)));
      panel.add(anyComponent(), gbc(3, 0, defaultSpec().withInsetLeft(5)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand
         .add(seq()
                 .add(aComponent(), spec().withIPad(4))
                 .add(aComponent(), spec().withGridSize(2, 2))
                 .add(aComponent())
                 .addLineBreak()
                 .add(aComponent()))
         .getComponent();

      verify(panel);
   }

   @Test
   public void subsequentLineBreaksCumulate()
   {
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInsetTop(5)));
      panel.add(anyComponent(), gbc(0, 4, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand
         .addLineBreak()
         .addLineBreak()
         .add(aComponent())
         .addLineBreak()
         .addLineBreak()
         .add(aComponent())
         .getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingNullComponentThenNoAction()
   {
      replay(panel);

      panelCommand
         .add((JComponent) null)
         .getComponent();

      verify(panel);
   }

   @Test
   public void whenAllLineLengthUsedForComponentsWithGridHeightRemainderThenNoMoreComponentsArePlaced()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridHeightRemainder()));

      replay(panel);

      panelCommand
         .withLineLength(1)
         .add(aComponent(), spec().withGridHeightRemainder())
         .add(aComponent(), spec().withIPad(1))
         .add(aComponent(), spec().withIPad(2))
         .add(aComponent(), spec().withIPad(3))
         .getComponent();

      verify(panel);
   }
}
