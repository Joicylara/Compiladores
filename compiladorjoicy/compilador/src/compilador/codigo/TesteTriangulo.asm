 section .data
	fmtin:	db "%d",  0x0
 	fmtout:	db "%d", 0xA, 0x0
	str_1: db "primeiro lado", 10,0
	str_2: db "segundo lado", 10,0
	str_3: db "terceiro lado", 10,0
	str_4: db "eh um Triangulo", 10,0
	str_5: db "Triangulo equilatero", 10,0
	str_6: db "Triangulo isosceles", 10,0
	str_7: db "Triangulo isosceles", 10,0
	str_8: db "Triangulo isosceles", 10,0
	str_9: db "Triangulo escaleno", 10,0
	str_10: db "Nao eh um Triangulo", 10,0

 section .bss
 	s3: resd 1
	a: resd 1
	b: resd 1
	c: resd 1
	d: resd 1
	s1: resd 1
	s2: resd 1

 section .text
	global main
	extern printf
	extern scanf

main:
 
  ; Preparação da pilha 
	push ebp
 	mov ebp,esp

; Escrever a string na saída
	push dword str_1
	call printf
	add esp, 4

 ; Ler a entrada do usuário para a variável
	push a
	push dword fmtin
	call scanf
	add esp, 8

; Escrever a string na saída
	push dword str_2
	call printf
	add esp, 4

 ; Ler a entrada do usuário para a variável
	push b
	push dword fmtin
	call scanf
	add esp, 8

; Escrever a string na saída
	push dword str_3
	call printf
	add esp, 4

 ; Ler a entrada do usuário para a variável
	push c
	push dword fmtin
	call scanf
	add esp, 8

	mov eax, [a]
	mov ebx, [b]
	add eax, ebx
	mov [s1], eax

	mov eax, [a]
	mov ebx, [c]
	add eax, ebx
	mov [s2], eax

	mov eax, [b]
	mov ebx, [c]
	add eax, ebx
	mov [s3], eax

	mov eax, [s1]
	mov ebx, [c]
	cmp eax, ebx
	jle _L1; menor ou igual

	mov eax, [s2]
	mov ebx, [b]
	cmp eax, ebx
	jle _L2; menor ou igual

	mov eax, [s3]
	mov ebx, [a]
	cmp eax, ebx
	jle _L3; menor ou igual

	mov eax,1
	mov [d], eax

; Escrever a string na saída
	push dword str_4
	call printf
	add esp, 4
_L3:
_L2:

	mov eax, [d]
	mov ebx, 1
	cmp eax, ebx
	jne _L4; salta se nao igual

	mov eax, [a]
	mov ebx, [b]
	cmp eax, ebx
	jne _L5; salta se nao igual

	mov eax, [b]
	mov ebx, [c]
	cmp eax, ebx
	jne _L6; salta se nao igual

; Escrever a string na saída
	push dword str_5
	call printf
	add esp, 4
_L6:
_L5:

	mov eax, [a]
	mov ebx, [b]
	cmp eax, ebx
	jne _L7; salta se nao igual

	mov eax, [b]
	mov ebx, [c]
	cmp eax, ebx
	je _L8; salta se igual

	mov eax, [c]
	mov ebx, [a]
	cmp eax, ebx
	je _L9; salta se igual

; Escrever a string na saída
	push dword str_6
	call printf
	add esp, 4
_L9:
_L8:
_L7:

	mov eax, [a]
	mov ebx, [b]
	cmp eax, ebx
	je _L10; salta se igual

	mov eax, [b]
	mov ebx, [c]
	cmp eax, ebx
	je _L11; salta se igual

	mov eax, [c]
	mov ebx, [a]
	cmp eax, ebx
	jne _L12; salta se nao igual

; Escrever a string na saída
	push dword str_7
	call printf
	add esp, 4
_L12:
_L11:
_L10:

	mov eax, [a]
	mov ebx, [b]
	cmp eax, ebx
	je _L13; salta se igual

	mov eax, [b]
	mov ebx, [c]
	cmp eax, ebx
	jne _L14; salta se nao igual

	mov eax, [c]
	mov ebx, [a]
	cmp eax, ebx
	je _L15; salta se igual

; Escrever a string na saída
	push dword str_8
	call printf
	add esp, 4
_L15:
_L14:
_L13:

	mov eax, [a]
	mov ebx, [b]
	cmp eax, ebx
	je _L16; salta se igual

	mov eax, [b]
	mov ebx, [c]
	cmp eax, ebx
	je _L17; salta se igual

	mov eax, [a]
	mov ebx, [c]
	cmp eax, ebx
	je _L18; salta se igual

; Escrever a string na saída
	push dword str_9
	call printf
	add esp, 4
_L18:
_L17:
_L16:
_L4:
_L1:

	mov eax, [d]
	mov ebx, 0
	cmp eax, ebx
	jne _L19; salta se nao igual

; Escrever a string na saída
	push dword str_10
	call printf
	add esp, 4
_L19:
;final do programa
	mov esp,ebp
 	pop ebp
 	ret