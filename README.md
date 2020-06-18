# SimPL Interpreter

A interpreter for the programming language *SimPL* -- simplified version of *ML*. Preliminary as it looks, the language supports both functional and imperative programming paradigms. It also features static type inference and type check, rendering it an easy-to-use and safe language for teaching purpose.

## Runtime Environment

This project is developed in Java under IntelliJ IDEA Community Edition, and runs in JRE 1.8 or higher. It carries the runtime library *JFlex* and  *Java CUP* as lexer and parser.

## Example Program

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
let iter = rec g =>
  fn f => fn i =>
    if i = 10 then 1 else f (g f (i+1))
in
let f = (fn x => x*x) in
  iter f 0
end
end
```

## Documentation

A comprehensive documentation is under the root directory. You can find almost everything there -- the language definition, the typing system, and other advanced features.