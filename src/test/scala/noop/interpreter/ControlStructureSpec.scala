/**
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package noop.interpreter;

import org.scalatest.matchers.ShouldMatchers;
import org.scalatest.Spec;

import model.{Expression, Block, BooleanLiteralExpression, WhileLoop, Visitor};
import types.NoopObject;

/**
 * @author alexeagle@google.com (Alex Eagle)
 * @author tocman@gmail.com (Jeremie Lenfant-Engelmann)
 */
class ControlStructureSpec extends Spec with ShouldMatchers with MockContext {

  class TrueThenFalseExpression(timesToReturnTrue: Int) extends Expression {
    var called = 0;

    def accept(visitor: Visitor) = {
      called += 1;
      new BooleanLiteralExpression(called <= timesToReturnTrue).accept(visitor);
    };
  }

  describe("the while loop") {

    it("should execute the body when the condition is true") {
      val context = fixture;
      val block: Block = new Block();
      val expression: MockExpression = new MockExpression();
      val visitor = new InterpreterVisitor(context);
      block.statements += expression;

      val whileLoop = new WhileLoop(new TrueThenFalseExpression(1), block);
      whileLoop.accept(visitor);
      expression.timesCalled should be(1);
    };

    it("should not execute the body when the condition is false") {
      val context = fixture;
      val block: Block = new Block();
      val expression: MockExpression = new MockExpression();
      val visitor = new InterpreterVisitor(context);
      block.statements += expression;

      val whileLoop = new WhileLoop(new BooleanLiteralExpression(false), block);
      whileLoop.accept(visitor);
      expression.timesCalled should be(0);
    };

    it("should execute the body repeatedly as long as the condition is true") {
      val context = fixture;
      val block: Block = new Block();
      val expression: MockExpression = new MockExpression();
      val visitor = new InterpreterVisitor(context);
      block.statements += expression;

      val whileLoop = new WhileLoop(new TrueThenFalseExpression(3), block);
      whileLoop.accept(visitor);
      expression.timesCalled should be(3);
    };
  };
}