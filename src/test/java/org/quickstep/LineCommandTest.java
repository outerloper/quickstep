package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.junit.Before;
import org.junit.Test;
import org.quickstep.command.PanelCommand;

import static org.easymock.EasyMock.*;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.TestUtils.*;

public class LineCommandTest
{
   private JPanel panel = createMock(JPanel.class);
   private PanelCommand panelCommand;

   @Before
   public void setUp()
   {
      panel.setLayout((LayoutManager) anyObject());
      panelCommand = panel().on(panel);
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
   public void whenLineCellSpecifiedThenOverridePanelCellWithIt()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(10).withInsetTop(20)));

      replay(panel);

      panelCommand.
         withCell(1, 0, spec().withInset(10)).
         add(line().
                withCell(1, spec().withInsetTop(20)).
                add(aComponent()).
                add(aComponent())
         ).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenLineCellSpecifiedMoreThanOnceItIsOverridden()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(10).withInsetY(30)));

      replay(panel);

      panelCommand.
         withCell(1, 0, spec().withInset(10)).
         add(line().
                withCell(1, spec().withInsetTop(20)).
                withCell(1, spec().withInsetTop(30)).
                withCell(1, spec().withInsetBottom(30)).
                add(aComponent()).
                add(aComponent())
         ).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenLineDefaultsSpecifiedThenOverridePanelRowSpecWithIt()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInset(10).withInsetTop(20)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(10).withInsetTop(20)));

      replay(panel);

      panelCommand.
         withRow(0, spec().withInset(10)).
         add(line().
                withDefault(spec().withInsetTop(20)).
                add(aComponent()).
                add(aComponent())
         ).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenLineDefaultsSpecifiedMoreThanOnceItIsOverridden()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInset(10, 30)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withInset(10, 30)));

      replay(panel);

      panelCommand.
         withRow(0, spec().withInset(10)).
         add(line().
                withDefault(spec().withInsetTop(20)).
                withDefault(spec().withInsetTop(30)).
                withDefault(spec().withInsetBottom(30)).
                add(aComponent()).
                add(aComponent())
         ).
         getComponent();

      verify(panel);
   }

   @Test
   public void lineCellSpecOverridesLineDefaultSpec()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withInsetTop(20)));
      panel.add(anyComponent(), gbc(1, 0, defaultSpec().withGap(30, 5)));

      replay(panel);

      panelCommand.
         add(line().
                withDefault(spec().withInsetTop(20)).
                withCell(1, spec().withInsetTop(30)).
                add(aComponent()).
                add(aComponent())
         ).
         getComponent();

      verify(panel);
   }

   @Test
   public void lineSpecsApplyOnlyToAppropriateGridBagLine()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(30)));
      panel.add(anyComponent(), gbc(1, 1, defaultSpec().withGap(30, 30)));
      panel.add(anyComponent(), gbc(0, 2, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         add(aComponent()).
         add(line().
                withDefault(spec().withInsetTop(30)).
                withCell(1, spec().withInsetLeft(30)).
                add(aComponent()).
                add(aComponent())
         ).
         add(aComponent()).
         getComponent();

      verify(panel);
   }

   @Test
   public void givenPanelWithLineLength1WhenAdding2LinesWithOneElementThenThoseElementsAddedInSeparateLines()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         withLineLength(1).
         add(line().add(aComponent())).
         add(line().add(aComponent())).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingLineWithElementWithGridWidthRemainderAndLineWithAnotherElementThenThoseElementsAddedInSeparateLines()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridWidthRemainder()));
      panel.add(anyComponent(), gbc(0, 1, defaultSpec().withInsetTop(5)));

      replay(panel);

      panelCommand.
         add(line().add(aComponent(), spec().withGridWidthRemainder())).
         add(line().add(aComponent())).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingALineWhoseElementsAreNotLaidOutInOneLineBecauseOfGridWidthRemainderThenOnlyThoseForCurrentLineArePlaced()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withGridWidthRemainder()));

      replay(panel);

      panelCommand.
         add(line().
                add(aComponent(), spec().withGridWidthRemainder()).
                add(aComponent())
         ).
         getComponent();

      verify(panel);
   }

   @Test
   public void whenAddingALineWhoseElementsAreNotLaidOutInOneLineBecauseOfLineLengthThenOnlyThoseForCurrentLineArePlaced()
   {
      panel.add(anyComponent(), gbc(0, 0, defaultSpec().withIPad(1)));

      replay(panel);

      panelCommand.
         withLineLength(1).
         add(line().
                add(aComponent(), spec().withIPad(1)).
                add(aComponent(), spec().withIPad(2)).
                add(aComponent(), spec().withIPad(3))
         ).
         getComponent();

      verify(panel);
   }
}
