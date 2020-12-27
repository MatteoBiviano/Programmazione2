type ide = string;;
type exp = Eint of int | Ebool of bool | Den of ide | Prod of exp * exp | Sum of exp * exp | Diff of exp * exp |
	Eq of exp * exp | Minus of exp | IsZero of exp | Or of exp* exp | And of exp* exp | Not of exp |
	Ifthenelse of exp * exp * exp | Let of ide * exp * exp | Fun of ide * exp | FunCall of exp * exp |
	Letrec of ide * exp * exp
	| ETree of tree (* gli alberi sono anche espressioni *)
	| ApplyOver of exp * exp (* applicazione di funzione ai nodi *)
	| Update of (ide list) * exp * exp (* aggiornamento di un nodo *)
	| Select of (ide list) * exp * exp (* selezione condizionale di un nodo *)
and tree = Empty (* albero vuoto *)
	| Node of ide * exp * tree * tree;;(* albero binario *)

(*ambiente polimorfo*)
type 't env = ide -> 't;;
let emptyenv (v : 't) = function x -> v;;
let applyenv (r : 't env) (i : ide) = r i;;
let bind (r : 't env) (i : ide) (v : 't) = function x -> if x = i then v else applyenv r x;;

(*tipi esprimibili*)
type evT = Int of int | Bool of bool | Unbound | FunVal of evFun | RecFunVal of ide * evFun 
 		   |TreeVal of evTree
and evTree= 
		|Empty
 		|Node of ide * evT * evTree * evTree
and evFun = ide * exp * evT env;;

(*rts*)
(*type checking*)
let typecheck (s : string) (v : evT) : bool = match s with
   "int" -> (match v with
      Int(_) -> true 
     | _ -> false)|
   "bool" -> (match v with
     Bool(_) -> true 
     | _ -> false) |
   "tree" -> (match v with
     TreeVal(_) -> true 
     | _ -> false) |
   _ -> failwith("Not a valid type");;


(*funzioni primitive*)
let prod x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n*u)
	 	| (_, _) -> failwith("Not a valid match"))
	else failwith("Type error");;

let sum x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n+u)
		| (_, _) -> failwith("Not a valid match"))
	else failwith("Type error");;

let diff x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Int(n-u)
		| (_, _) -> failwith("Not a valid match"))
	else failwith("Type error");;

let eq x y = if (typecheck "int" x) && (typecheck "int" y)
	then (match (x,y) with
		(Int(n),Int(u)) -> Bool(n=u)
		| (_, _) -> failwith("Not a valid match"))
	else failwith("Type error");;

let minus x = if (typecheck "int" x) 
	then (match x with
	   	Int(n) -> Int(-n)
	   	| _ -> failwith("Not a valid match"))
	else failwith("Type error");;

let iszero x = if (typecheck "int" x)
	then (match x with
		Int(n) -> Bool(n=0)
		| _ -> failwith("Not a valid match"))
	else failwith("Type error");;

let vel x y = if (typecheck "bool" x) && (typecheck "bool" y)
	then (match (x,y) with
		(Bool(b),Bool(e)) -> (Bool(b||e))
		| (_,_) -> failwith("Not a valid match"))
	else failwith("Type error");;

let et x y = if (typecheck "bool" x) && (typecheck "bool" y)
	then (match (x,y) with
		(Bool(b),Bool(e)) -> Bool(b&&e)
		| (_,_) -> failwith("Not a valid match"))
	else failwith("Type error");;

let non x = if (typecheck "bool" x)
	then (match x with
		Bool(true) -> Bool(false) 
		| Bool(false) -> Bool(true)	
		| _ -> failwith("Not a valid match"))
	else failwith("Type error");;

(*interprete*)
let rec eval (e : exp) (r : evT env) : evT = match e with
	Eint n -> Int n |
	Ebool b -> Bool b |
	IsZero a -> iszero (eval a r) |
	Den i -> applyenv r i |
	Eq(a, b) -> eq (eval a r) (eval b r) |
	Prod(a, b) -> prod (eval a r) (eval b r) |
	Sum(a, b) -> sum (eval a r) (eval b r) |
	Diff(a, b) -> diff (eval a r) (eval b r) |
	Minus a -> minus (eval a r) |
	And(a, b) -> et (eval a r) (eval b r) |
	Or(a, b) -> vel (eval a r) (eval b r) |
	Not a -> non (eval a r) |
	Ifthenelse(a, b, c) -> 
		let g = (eval a r) in
			if (typecheck "bool" g) 
				then (if g = Bool(true) then (eval b r) else (eval c r))
				else failwith ("nonboolean guard") |
	Let(i, e1, e2) -> eval e2 (bind r i (eval e1 r)) |
	Fun(i, a) -> FunVal(i, a, r) |
	FunCall(f, eArg) -> 
		let fClosure = (eval f r) in
			(match fClosure with
				FunVal(arg, fBody, fDecEnv) -> 
					eval fBody (bind fDecEnv arg (eval eArg r)) |
				RecFunVal(g, (arg, fBody, fDecEnv)) -> 
					let aVal = (eval eArg r) in
						let rEnv = (bind fDecEnv g fClosure) in
							let aEnv = (bind rEnv arg aVal) in
								eval fBody aEnv |
				_ -> failwith("non functional value")) |
    Letrec(f, funDef, letBody) ->
        (match funDef with
           	Fun(i, fBody) -> let r1 = (bind r f (RecFunVal(f, (i, fBody, r)))) in
                  			                eval letBody r1 |
            _ -> failwith("non functional def"))|
    ETree tr -> TreeVal(eval_t tr r)|
	ApplyOver(ex,tr)->
		(match tr with
			ETree t -> TreeVal(apply_o ex t r)
			|_ -> failwith("Not a valid match"))|
	Update (lis, ex, tr) -> 
		(match tr with
			ETree t -> TreeVal(update lis ex t r)
			|_ -> failwith("Not a valid match"))|
	Select(lis, ex, tr) ->
		(match tr with
			ETree t -> TreeVal(select lis ex t r)
			|_ -> failwith("Not a valid match"))

(*Funzioni ausiliarie per semplificare l'interprete*)

(*Valutazione di un albero (espressione)*)
and eval_t (tr : tree) (r : evT env) : evTree =
	(match tr with
	Empty -> Empty
	|Node(id, e, t1, t2) -> Node(id, eval e r, eval_t t1 r, eval_t t2 r))

(*Applicazione di funzione*)
and apply_o (f : exp) (tr : tree) (r: evT env) : evTree =
	(match tr with
	Empty -> Empty
	|Node(id, e, t1, t2) -> let e1 = (eval (FunCall(f,e)) r) in
							  	Node(id, e1, apply_o f t1 r, apply_o f t2 r))

(*Aggiornamento di un nodo*)
and update (ls : (ide list)) (f : exp) (tr : tree) (r : evT env) : evTree =
	(match ls, tr with
		[x], Node(id, e, t1, t2) when id = x -> let e1 = (eval (FunCall(f,e)) r) in
													Node(id, e1, eval_t t1 r, eval_t t2 r)
		|x::xs, Node(id, e, t1, t2) when id = x -> let e1 = (eval e r) in
		 											Node(id, e1, update xs f t1 r, update xs f t2 r)
		|_,_ -> (eval_t tr r) ) 

(*Selezione di un sottoalbero*)
and select (ls : (ide list)) (f : exp) (tr : tree) (r : evT env) : evTree =
	(match ls, tr with
		[x], Node(id, e, t1, t2) -> let g = (eval (FunCall(f, e)) r) in
			 							if (typecheck "bool" g) 
											then (if (g = Bool(true) && x=id) 
												 	then Node(id, eval e r, eval_t t1 r, eval_t t2 r)
												  else Empty)
										else failwith ("non boolean guard") 
		|x::xs, Node(id, e, t1, t2) when id = x -> let selt1 = select xs f t1 r in
												 		let selt2 = select xs f t2 r in
												 			(match selt1, selt2 with
												 				Empty, Empty -> Empty
												 				|t, Empty -> t
												 			 	|_, t -> t)
		|_,_ -> Empty);;

(* =============================  TESTS  =================== *)

(* basico: no let *) 
let env0 = emptyenv Unbound;;

let e1 = FunCall(Fun("y", Sum(Den "y", Eint 1)), Eint 3);;

eval e1 env0;;

let e2 = FunCall(Let("x", Eint 2, Fun("y", Sum(Den "y", Den "x"))), Eint 3);;

eval e2 env0;;

(*Alberi di interi*)
let tree1 = ETree(Node("a", Sum(Eint 11, Eint 4), Node("b", Diff(Eint 12, Eint 9), Empty, Node("c", Prod(Eint 3, Eint 4), Empty, Empty)), Empty));;
eval tree1 env0;;

let tree2 = ETree(Node("r", Eint 5, 
					Node("s", Eint 3, Empty, Node("w", Eint 4, Empty, Empty)),
				  	Node("q", Eint 1, 
				  		Node("p", Eint 5, Empty, Empty), 
				  			Node("p", Eint 5, 
				  				Node("z", Eint 4, Empty, 
				  		 			Node("t", Eint 7, Empty, Empty)), 
				  			 			Node("i", Eint 3, Empty, Empty)))));;
eval tree2 env0;;

(*Albero di booleani*)
let tree3 = ETree(Node("a", And(Ebool true, Ebool false), Node("b", Or(Ebool true, Ebool false), Empty, Node("c", Not(Ebool false), Empty, Empty)), Empty));;
eval tree3 env0;;

(*Funzioni usate nei test*)
let quadrato = Fun("x", Prod(Den "x", Den "x"));;
let somma4= Fun("x", Sum(Den "x", Eint 4));;
let not_val = Fun("x", Not(Den "x"));;
let is5 = Fun("x", Eq(Den "x", Eint 5));;
let is0 = Fun("x", Eq(Den "x", Eint 0));;

(*ApplyOver con funzione quadrato*)
let app_o1 = ApplyOver(quadrato, tree2);;
eval app_o1 env0;;

(*ApplyOver con funzione not_val*)
let app_o2 = ApplyOver(not_val, tree3);;
eval app_o2 env0;;

(*Update con funzione quadrato*)
let update1 = Update(["r";"s";"w"], quadrato, tree2);; 
eval update1 env0;; (*Applica quadrato al nodo di tag "w" individuato dal cammino*)

(*Update con funzione somma4*)
let update2 = Update([], somma4, tree2);; 
eval update2 env0;; (*Applica somma4 ai due nodi individuati dal cammino*)

(*Select con funzione is5*)
let select1 = Select(["r";"q";"p"], is5, tree2);;
eval select1 env0;;	 
(*Restituisce Node ("p", Int 5, 
*				Node ("z", Int 4, Empty, Node ("t", Int 7, Empty, Empty)),
*				Node ("i", Int 3, Empty, Empty))
*)

(*Select con funzione is0*)
let select2 = Select(["r";"s"], is0, tree2);;
eval select2 env0;; (*Restituisce un albero vuoto*)

(*Select con funzione quadrato || non valutabile *)
let select3 = Select(["r";"s"], quadrato, tree2);;
eval select3 env0;; (*Lancia eccezione "Failure: non boolean guard"*)