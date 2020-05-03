# SimPL Interpreter

A interpreter for the programming language *SimPL* -- simplified version of *ML*. Preliminary as it looks, the language supports both functional and imperative programming paradigms. It also features static type inference and type check, rendering it an easy-to-use and safe language for teaching purpose.

## The Language SimPL

### Syntax

$$
\begin{align}
	t ::= &\quad x  \tag{identifiers}\\
  | &\quad n  \tag{integers}\\
	| &\quad \texttt{true}  \tag{true}\\
	| &\quad \texttt{false}  \tag{false}\\
	| &\quad \texttt{nil}  \tag{empty list}\\
	| &\quad (t,t)  \tag{pair}\\
	| &\quad \sharp t  \tag{unary op}\\
	| &\quad t \otimes t  \tag{binary op}\\
	| &\quad \texttt{fn }x \texttt{ => } t  \tag{function def}\\
	| &\quad \texttt{rec }x \texttt{ => } t  \tag{recursion def}\\
	| &\quad t\ t  \tag{application}\\
	| &\quad \texttt{if }t\texttt{ then }t\texttt{ else }t  \tag{condition}\\
	| &\quad \texttt{let }x=t\texttt{ in }t\texttt{ end}  \tag{binding}\\
	| &\quad \texttt{while }t\texttt{ do }t  \tag{loop}\\
	| &\quad (t)  \tag{group}\\
	| &\quad ()  \tag{unit}
\end{align}
$$

where $\sharp \in \{\text{~}, \text{not}, ! \}$, and $\otimes \in \{ +, -, *, /, \%, ::, =, <, <=, >, >=, <>, :=, \text{andalso}, \text{orelse} \}$.

### Types

$$
\begin{align}
	T ::= &\quad \texttt{int}  \tag{integer type}\\
	|&\quad \texttt{bool}  \tag{boolean type}\\
	|&\quad \texttt{unit}  \tag{unit type}\\
	|&\quad \texttt{list}(T)  \tag{list type}\\
	|&\quad \texttt{ref}(T)  \tag{reference type}\\
	|&\quad T \times T  \tag{pair type}\\
	|&\quad T \to T  \tag{function type}
\end{align}
$$

Even though SimPL is a strongly-typed language, users are not expected to annotate the types manually. All types can be automatically inferred from the syntax structure. This feature gives programmers much freedom.

### Typing Rules



### Operational Semantics



### Example Program

1. The following program first declares a function `f` that returns the square of its argument. It then allocates two memory cells, one referred by `y` and initialized to the integer 1, the other referred by `i` and initialized to 0. In the while loop, `f` is applied repeatedly for ten times and, after that, the content in `y` is returned.

```ocaml
let f = (fn x => x*x) in
let y = ref 1 in
  let i = ref 0 in
    while !i < 10 do
      y := f(!y);
      i := !i + 1
  end;
  !y
end
end
```

2. The following program performs the same task in functional-programming.

```ocaml
let iter =
  rec g => fn f => fn i =>
    if i = 10 then 1
    else f (g f (i+1))
in
  let f = (fn x => x*x) in
     iter f 0
  end
end
```

## Implementation Overview

### Runtime Environment

This project is written in Java under IntelliJ IDEA, and runs in JRE 1.8 or higher. It carries the runtime library *Java CUP* that serves as lexer and parser.

### Project Structure



### Evaluation



### Typing

