 section .data
	fmtin:	db "%d",  0x0
 	fmtout:	db "%d", 0xA, 0x0
	str_1: db "digite um numero", 10,0
	str_2: db "digite um numero", 10,0
	str_3: db "digite um numero", 10,0
	str_4: db " nao e triangulo", 10,0
	str_5: db "e triangulo", 10,0
	str_6: db "escaleno", 10,0
	str_7: db "isoceles", 10,0
	str_8: db "equilatero", 10,0

 section .bss
 	a: resd 1
	b: resd 1
	c: resd 1
	d: resd 1

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
	mov ebx, [c]
	cmp eax, ebx
	jae _L1

	mov eax, [d]
	mov ebx, 1
	sub eax, ebx
	mov [d], eax
_L1:

	mov eax, [c]
	mov ebx, [a]
	cmp eax, ebx
	jae _L2

	mov eax, [d]
	mov ebx, 1
	sub eax, ebx
	mov [d], eax
_L2:

	mov eax, [b]
	mov ebx, [c]
	cmp eax, ebx
	jae _L3

	mov eax, [d]
	mov ebx, 1
	sub eax, ebx
	mov [d], eax
_L3:

	mov eax, 0
	mov ebx, [d]
	cmp eax, ebx
	jae _L4

; Escrever a string na saída
	push dword str_4
	call printf
	add esp, 4

	jmp _L5
_L4:

; Escrever a string na saída
	push dword str_5
	call printf
	add esp, 4

	mov eax, [b]
	mov ebx, [c]
	cmp eax, ebx
	jne _L6

	mov eax, [d]
	mov ebx, 1
	add eax, ebx
	mov [d], eax
_L6:

	mov eax, [a]
	mov ebx, [c]
	cmp eax, ebx
	jne _L7

	mov eax, [d]
	mov ebx, 1
	add eax, ebx
	mov [d], eax
_L7:

	mov eax, [b]
	mov ebx, [a]
	cmp eax, ebx
	jne _L8

	mov eax, [d]
	mov ebx, 1
	add eax, ebx
	mov [d], eax
_L8:

	mov eax, [d]
	mov ebx, 1
	cmp eax, ebx
	jae _L9

; Escrever a string na saída
	push dword str_6
	call printf
	add esp, 4
_L9:

	mov eax, [d]
	mov ebx, 1
	cmp eax, ebx
	jne _L10

; Escrever a string na saída
	push dword str_7
	call printf
	add esp, 4
_L10:

	mov eax, 1
	mov ebx, [d]
	cmp eax, ebx
	jae _L11

; Escrever a string na saída
	push dword str_8
	call printf
	add esp, 4
_L11:
_L5:

	mov esp,ebp
 	pop ebp
 	ret