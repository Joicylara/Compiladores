 section .data
	fmtin:	db "%d",  0x0
 	fmtout:	db "%d", 0xA, 0x0
	str_1: db "base do triagulo", 10,0
	str_2: db " ", 10,0
	str_3: db " ", 10,0

 section .bss
 	a: resd 1
	b: resd 1
	r: resd 1
	c: resd 1
	s: resd 1
	d: resd 1
	e: resd 1
	f: resd 1
	g: resd 1
	h: resd 1
	m: resd 1

 section .text
	global main
	extern printf
	extern scanf

main:
 
  ; Preparação da pilha 
	push ebp
 	mov ebp,esp

	mov eax,0
	mov [c], eax

; Escrever a string na saída
	push dword str_1
	call printf
	add esp, 4

 ; Ler a entrada do usuário para a variável
	push b
	push dword fmtin
	call scanf
	add esp, 8
_L1:

	mov eax, [c]
	mov ebx, [b]
	cmp eax, ebx
	jge _L2; maior ou igual

	mov eax,0
	mov [a], eax

	mov eax, [c]
	mov ebx, 1
	add eax, ebx
	mov [s], eax
_L3:

	mov eax, [a]
	mov ebx, [s]
	cmp eax, ebx
	jge _L4; maior ou igual

	mov eax, [c]
	mov ebx, 1
	add eax, ebx
	mov [s], eax

	mov eax,1
	mov [d], eax

	mov eax, [c]
	mov ebx, 1
	add eax, ebx
	mov [e], eax

	mov eax,1
	mov [f], eax
_L5:

	mov eax, [f]
	mov ebx, [e]
	cmp eax, ebx
	jge _L6; maior ou igual

	mov eax, [d]
	mov ebx, [f]
	mul ebx
	mov [d], eax

	mov eax, [f]
	mov ebx, 1
	add eax, ebx
	mov [f], eax

	;Salta para a instrução do rótulo passado

	jmp _L5
_L6:

	mov eax,1
	mov [g], eax

	mov eax, [a]
	mov ebx, 1
	add eax, ebx
	mov [e], eax

	mov eax,1
	mov [f], eax
_L7:

	mov eax, [f]
	mov ebx, [e]
	cmp eax, ebx
	jge _L8; maior ou igual

	mov eax, [g]
	mov ebx, [f]
	mul ebx
	mov [g], eax

	mov eax, [f]
	mov ebx, 1
	add eax, ebx
	mov [f], eax

	;Salta para a instrução do rótulo passado

	jmp _L7
_L8:

	mov eax,1
	mov [h], eax

	mov eax, [c]
	mov ebx, [a]
	sub ebx, ecx
	mov [e], eax

	mov eax, [e]
	mov ebx, 1
	add eax, ebx
	mov [e], eax

	mov eax,1
	mov [f], eax
_L9:

	mov eax, [f]
	mov ebx, [e]
	cmp eax, ebx
	jge _L10; maior ou igual

	mov eax, [g]
	mov ebx, [f]
	mul ebx
	mov [g], eax

	mov eax, [f]
	mov ebx, 1
	add eax, ebx
	mov [f], eax

	;Salta para a instrução do rótulo passado

	jmp _L9
_L10:

	mov eax, [g]
	mov ebx, [h]
	mul ebx
	mov [m], eax

	mov eax, [d]
	mov ebx, [m]
	xor edx, edx
	div ebx
	mov [r], eax

; Escrever a variável na saída
	push dword [r]
	push dword fmtout
	call printf
	add esp, 8

; Escrever a string na saída
	push dword str_2
	call printf
	add esp, 4

	mov eax, [a]
	mov ebx, 1
	add eax, ebx
	mov [a], eax

	;Salta para a instrução do rótulo passado

	jmp _L3
_L4:

; Escrever a string na saída
	push dword str_3
	call printf
	add esp, 4

	mov eax, [c]
	mov ebx, 1
	add eax, ebx
	mov [c], eax

	;Salta para a instrução do rótulo passado

	jmp _L1
_L2:
;final do programa
	mov esp,ebp
 	pop ebp
 	ret