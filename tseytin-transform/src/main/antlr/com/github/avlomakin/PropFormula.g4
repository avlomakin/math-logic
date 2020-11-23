grammar PropFormula;

formula
   : NOT? '(' formula ')'                                                           #parenthesis
   | formula SP? LOGICAL_AND SP? formula                                            #logicalAnd
   | formula SP? LOGICAL_OR SP? formula                                             #logicalOr
   | formula SP? LOGICAL_IMPLY SP? formula                                          #logicalImply
   | NOT? PROP_VARIABLE                                                             #literal
   ;

LOGICAL_AND: '&';
LOGICAL_OR: '|' ;
LOGICAL_IMPLY: '->';
NOT : '!' ;
SP : ' ';

PROP_VARIABLE : ALPHA PROP_VARIABLE_CHAR* ;

fragment PROP_VARIABLE_CHAR : '_' | DIGIT | ALPHA;

fragment DIGIT : ('0'..'9');

fragment ALPHA
   : ( 'A'..'Z' | 'a'..'z' )
   ;

//string declaration
STRING
   : '"' (ESC | ~ ["\\])* '"'
   ;
fragment ESC
   : '\\' (["\\/bfnrt] | UNICODE)
   ;
fragment UNICODE
   : 'u' HEX HEX HEX HEX
   ;
fragment HEX
   : [0-9a-fA-F]
   ;
