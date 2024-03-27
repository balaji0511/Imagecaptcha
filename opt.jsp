

<%!int[] getOPT()
{

java.util.Random r=new java.util.Random();


int opt[]=new int[10];


opt[0]=r.nextInt(5);
System.out.println("Opt0"+opt[0]);

while(true)
{
boolean f=false;
opt[1]=r.nextInt(5);
for(int i=0;i<1;i++)
{
	if(opt[1]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt1"+opt[1]);

while(true)
{
boolean f=false;
opt[2]=r.nextInt(5);
for(int i=0;i<2;i++)
{
	if(opt[2]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt2"+opt[2]);


while(true)
{
boolean f=false;
opt[3]=r.nextInt(5);
for(int i=0;i<3;i++)
{
	if(opt[3]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt3"+opt[3]);

while(true)
{
boolean f=false;
opt[4]=r.nextInt(5);
for(int i=0;i<4;i++)
{
	if(opt[4]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt4"+opt[4]);

while(true)
{
boolean f=false;
opt[5]=r.nextInt(10);
if(opt[5]<5)opt[5]=opt[5]+5;
for(int i=0;i<5;i++)
{
	if(opt[5]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt5"+opt[5]);

while(true)
{
boolean f=false;
opt[6]=r.nextInt(10);
if(opt[6]<5)opt[7]=opt[6]+5;

for(int i=0;i<6;i++)
{
	if(opt[6]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt6"+opt[6]);

while(true)
{
boolean f=false;
opt[7]=r.nextInt(10);
if(opt[7]<5)opt[7]=opt[7]+5;

for(int i=0;i<7;i++)
{
	if(opt[7]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt7"+opt[7]);
while(true)
{
boolean f=false;
opt[8]=r.nextInt(10);
if(opt[8]<5)opt[8]=opt[8]+5;

for(int i=0;i<8;i++)
{
	if(opt[8]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt8"+opt[8]);

while(true)
{
boolean f=false;
opt[9]=r.nextInt(10);
if(opt[9]<5)opt[9]=opt[9]+5;

for(int i=0;i<9;i++)
{
	if(opt[9]==opt[i])
		{f=true;break;}
}
if(f==false)break;
}
System.out.println("Opt9"+opt[9]);
return opt;
}
%>
