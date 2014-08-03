package org.quickstep;

import java.awt.*;
import java.util.LinkedList;
import javax.swing.*;

import org.easymock.IArgumentMatcher;
import org.junit.*;

import static org.easymock.EasyMock.*;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.util.DebugSupport.gbcEquals;
import static org.quickstep.util.DebugSupport.gbcToString;

public class GridBagBuilderTest
{
   private JPanel panel;
   private PanelCommand panelCommand;

   static class GBCMatcher implements IArgumentMatcher
   {
      GridBagConstraints constraints;

      GBCMatcher(GridBagConstraints constraints)
      {
         this.constraints = constraints;
      }

      @Override
      public boolean matches(Object o)
      {
         return o instanceof GridBagConstraints && gbcEquals(constraints, (GridBagConstraints) o);
      }

      @Override
      public void appendTo(StringBuffer stringBuffer)
      {
         stringBuffer.append(gbcToString(constraints));
      }
   }

   static GBCMatcher gbc(int x, int y, CellSpec spec)
   {
      reportMatcher(new GBCMatcher(spec.toConstraints(x, y)));
      return null;
   }

   private static GridBagConstraints anyGbc()
   {
      return anyObject(GridBagConstraints.class);
   }

   private static JComponent anyComponent()
   {
      return anyObject();
   }

   private static JLabel aComponent()
   {
      return new JLabel();
   }

   private static CellSpec defaultSpec()
   {
      return spec().withAnchorX(AX.LEFT);
   }


   @Before
   public void setUp()
   {
      panel = createMock(JPanel.class);
      panel.setLayout((LayoutManager) anyObject());
      panelCommand = panel().with(panel);
   }

   @Test
   public void testAddComponents()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInsetLeft(5)));
      panel.add(anyComponent(), gbc(2, 0, defaultSpec().withInsetLeft(5)));

      replay(panel);

      panelCommand.
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void wrapRowWhenBuilderGridWidthIsSet()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInset(5, 0, 0, 0)));

      replay(panel);

      panelCommand.
         withMaxLineLength(2).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void wrapRowWhenNextRowInvoked()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));

      replay(panel);

      panelCommand.
         add(aComponent()).
         addLineBreak().
         add(aComponent()).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void wrapRowWhenRemainingGridWidthUsed()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridWidthRemaining()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));

      replay(panel);

      panelCommand.
         add(aComponent(), spec().withGridWidthRemaining()).
         add(aComponent()).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void specifyCellDefaultsMethodOverridesImplicitDefaultSpecForAddedComponents()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInset(0, 0, 3, 10)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 10, 3, 10).withGridWidthRemaining()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(3, 0, 3, 10)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(3, 10, 3, 10)));

      replay(panel);

      panelCommand.
         specifyDefault(spec().withInset(10, 3)).
         add(aComponent()).
         add(aComponent(), spec().withGridWidthRemaining()).
         add(aComponent()).
         add(aComponent()).
         getComponent();

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

      panelCommand.
         withMaxLineLength(2).
         specifyColumn(1, spec().withAnchorX(AX.RIGHT)).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         getComponent();

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

      panelCommand.
         withMaxLineLength(2).
         specifyRow(1, spec().withInsetTop(20)).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void cellSpecOverridesRowAndColumnSpec()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInset(10, 10, 0, 0)));

      replay(panel);

      panelCommand.
         withMaxLineLength(2).
         specifyCell(0, 0, spec().withInsetLeft(10).withInsetTop(10)).
         add(aComponent()).
         getComponent();

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

      panelCommand.
         withMaxLineLength(2).
         specifyRow(1, spec().withInsetBottom(20)).
         specifyColumn(1, spec().withInsetBottom(30)).
         addBlank().addBlank().addBlank().
         add(aComponent()).
         getComponent();

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

      panelCommand.
         withMaxLineLength(2).
         specifyColumn(1, spec().withInsetBottom(20)).
         specifyRow(1, spec().withInsetBottom(30)).
         addBlank().addBlank().addBlank().
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void specProvidedForAddOverridesAllOtherSpecsForCurrentCell()
   {
      panel.add(anyComponent(), gbc(0, 0, spec().withAnchor(AX.LEFT, AY.TOP)));

      replay(panel);

      panelCommand.
         withMaxLineLength(2).
         specifyCell(0, 0, spec().withAnchor(AX.RIGHT, AY.BOTTOM)).
         add(aComponent(), spec().withAnchor(AX.LEFT, AY.TOP)).
         getComponent();

      verify(panel);
   }

   @Test
   public void componentsArePlacedOnGridRespectingTheirGridWidth()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridWidth(2)));
      panel.add(anyComponent(), gbc(2, 0, defaultSpec().withInset(0, 5, 0, 0)));

      replay(panel);

      panelCommand.
         add(aComponent(), spec().withGridWidth(2)).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void componentsArePlacedOnGridRespectingTheirGridHeight()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridHeight(2)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));

      replay(panel);

      panelCommand.
         withMaxLineLength(2).
         add(aComponent(), spec().withGridHeight(2)).
         add(aComponent()).
         add(aComponent()).
         getComponent();

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

      panelCommand.
         withMaxLineLength(3).
         add(aComponent(), spec().withGridSize(2, 2)).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void noMoreComponentsArePlacedAtColumnWithComponentTakingRemainingGridHeight()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0).withGridHeightRemaining()));
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInset(5, 0, 0, 0)));
      panel.add(anyComponent(), gbc(0, 3, defaultSpec().withInset(5, 0, 0, 0)));

      replay(panel);

      panelCommand.
         withMaxLineLength(2).
         add(aComponent()).
         add(aComponent()).
         add(aComponent()).
         add(aComponent(), spec().withGridHeightRemaining()).
         add(aComponent()).
         add(aComponent()).
         getComponent();

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
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInset(0, 30, 0, 0)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(2, 0, defaultSpec().withInset(0, 5, 0, 0)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInset(5, 30, 0, 0)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withInset(5, 5, 0, 0)));
      panel.add(anyComponent(), gbc(2, 1, defaultSpec().withInset(5, 5, 0, 0)));

      replay(panel);

      panelCommand.
         withMaxLineLength(3).
         specifyColumn(0, spec().withAnchorX(AX.RIGHT).withInsetLeft(30)).
         addAll(checkBoxes, defaultSpec()).
         getComponent();

      verify(panel);
   }

   @Test
   public void addingBuilderUsesSpecOfThisBuilderToPlaceItsPanel()
   {
      JPanel auxPanel = new JPanel();

      panel.add(eq(auxPanel), gbc(0, 0, specWithFill().withInset(0, 0, 0, 50)));

      replay(panel);

      PanelCommand builder = panel().with(auxPanel).
         withSpec(specWithFill());
      panelCommand.
         specifyCell(0, 0, spec().withInsetRight(50)).
         add(builder).
         getComponent();

      verify(panel);
   }

   @Test
   public void addLineWithOneElementWithGridWidthRemainingAndThenAnotherLine()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridWidthRemaining()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         add(line().add(aComponent(), spec().withGridWidthRemaining())).
         add(line().add(aComponent())).
         getComponent();

      verify(panel);
   }

   @Test
   public void addingEmptyLinesDoesNotDoAnything()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         add(line()).
         add(line().add(aComponent())).
         add(line()).
         add(line()).
         add(line().add(aComponent())).
         add(line()).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingElementsAfterLineThenNewLineStarts()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInsetLeft(5)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(5)));
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         add(aComponent()).
         add(aComponent()).
         add(line().add(aComponent())).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void addingSubsequentLines()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInsetLeft(5)));
      panel.add(anyComponent(), gbc(2, 0, defaultSpec().withInsetLeft(5)));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(5)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withGap(5)));
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         add(line().add(aComponent()).add(aComponent()).add(aComponent())).
         add(line().add(aComponent()).add(aComponent())).
         add(line().add(aComponent())).
         getComponent();

      verify(panel);
   }

   @Test
   public void addingLineWithCellSpec()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withIPad(4)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInsetLeft(5).withGridSize(2, 2)));
      panel.add(anyComponent(), gbc(3, 0, defaultSpec().withInsetLeft(5)));

      replay(panel);

      panelCommand.
         add(line().
            add(aComponent(), spec().withIPad(4)).
            add(aComponent(), spec().withGridSize(2, 2)).
            add(aComponent())).
         getComponent();

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

      panelCommand.
         add(seq().
            add(aComponent(), spec().withIPad(4)).
            add(aComponent(), spec().withGridSize(2, 2)).
            add(aComponent()).
            addLineBreak().
            add(aComponent())).
         getComponent();

      verify(panel);
   }

   @Test
   public void subsequentLineBreaksCumulate() // TODO make a decision if line() and lineBreak should both always start a new line or not
   {
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInsetTop(5)));
      panel.add(anyComponent(), gbc(0, 4, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         addLineBreak().
         addLineBreak().
         add(aComponent()).
         addLineBreak().
         addLineBreak().
         add(aComponent()).
         getComponent();

      verify(panel);
   }
}
