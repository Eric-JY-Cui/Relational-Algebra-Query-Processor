# Relational-Algebra-Query-Processor
Developing a Relational Algebra Query Processor for a bonus assignment for a course. 
Allow user to perform basic algebra query process in a command prompt style. 
The main method and the start button can be found in the Command class.

The command operations are as follows:

relation x(a,b,c){l,m,n,o,p}   -- create (or update if exist) a relation x with attribute a, b, and c such that a(l,o), b(m,p), c(n,null)
relation x = <other command>   -- have relation x equal to the output of another command
get x   -- output the attributes of relation x in a table format
select x(m>3)   -- output the value of relation x with with a condition, in this case, when attribute m greater than 3
    accepted operators are: >, <, >=, <=, =, != (only = and != accepted for none integer value)
project x(m,n)   -- output relation x with only m and n as attribute
join x(m,n)y   -- output the value of relation x and relation y joined by attribute m in relation x and attribute n in relation y
intersect x(y)   -- output the intersection result of relation x and y
union x(y)   -- output the union result of relation x and y
minus x(y)   -- output the result of relation x subtracted by y

*you can access this list anytime by typing "help". 
*note that failsafe have not covered all typos, so make sure to follow the format strictly to prevent any errors. 

here are some command as a sample: (make sure to take out the comments to try it)


relation R1(a1,a2,a3){1,11,111,2,22,222,3,33} //this creates the relation R1 with a set attributes and values

get R1 //this display the attributes and values of R1. (note that the third index of a3 is null instead because 333 was missing from creation of R1)

select R1(a1!=3) //this should display the first and second index of the relation R1

relation R2 = select R1(a1!=3) //this transfer the output to R2. you can do the same with other operations that is not relation.

get R2 // you can see the selection of R1 as a value of R2. 

project R1(a1,a3) //this display the relation R1 with only attribute a1 and a3

relation R2(a1,a2,a4){1,77,1,3,33,2,8,88,2} //creates the relation R2 for showcasing upcoming feature. This also override the previous R2 value

join R1(a1=a4)R2 //this creates a relation that joins the two relation by R1's a1 and R2's a4, notice that value 2 of R1 appeared twice since it match with two of the value in R2

intersect R1(R2) //this is the intersection of R1 and R2. This should only display attribute a1 and a2 since they are the only common attributes, and value 3 of R1 and value 2 of R2 as they are the only value with same a1 and a2

union R1(R2) //same as previous, but display value 3 of R1 and value 2 of R2 together as one.

minus R1(R2) //this should be R1 but without the value 3


