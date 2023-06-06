 section .data
	fmtin:	db "%d",  0x0
 	fmtout:	db "%d", 0xA, 0x0
	str_1: db "Numero de linhas: ", 10,0
	str_2: db "Valor: ", 10,0
	str_3: db "Valor: ", 10,0
	str_4: db "Valor: ", 10,0

 section .bss
 	numero: resd 1
	coluna: resd 1
	cont2: resd 1
	cont1: resd 1
	valor: resd 1
	linha: resd 1

 section .text
	global _main
	extern _printf
	extern _scanf

_main:
 
  ; Preparação da pilha 
	push ebp
 	mov ebp,esp

; Escrever a string na saída
	push dword str_1
	call _printf
	add esp, 4

 ; Ler a entrada do usuário para a variável
	push numero
	push dword fmtin
	call _scanf
	add esp, 8

	mov eax,1
	mov [linha], eax

	mov eax, [numero]
	mov ebx, 1
	add eax, ebx
	mov [cont1], eax

	mov eax, [linha]
	mov ebx, 1
	add eax, ebx
	mov [cont2], eax
_L1:

	mov eax, [linha]
	mov ebx, [cont1]
	cmp eax, ebx
	jge _L2; maior ou igual

	mov eax,1
	mov [valor], eax

; Escrever a string na saída
	push dword str_2
	call _printf
	add esp, 4

; Escrever a variável na saída
	push dword [valor]
	push dword fmtout
	call _printf
	add esp, 8

	mov eax, [numero]
	mov ebx, 1
	cmp eax, ebx
	je _L3; salta se igual
_L4:

	mov eax, [linha]
	mov ebx, [numero]
	cmp eax, ebx
	jge _L5; maior ou igual

	mov eax,1
	mov [valor], eax

; Escrever a string na saída
	push dword str_3
	call _printf
	add esp, 4

; Escrever a variável na saída
	push dword [valor]
	push dword fmtout
	call _printf
	add esp, 8

	mov eax,1
	mov [coluna], eax
_L6:

	mov eax, [coluna]
	mov ebx, [cont2]
	cmp eax, ebx
	jge _L7; maior ou igual

	mov eax, [linha]
	mov ebx, [coluna]
	sub eax, ebx
	mov ecx, eax

	mov eax, ecx
	mov ebx, 1
	add eax, ebx
	mov edx, eax

	mov eax, [valor]
	mov ebx, edx
	mul ebx
	mov ecx, eax

	mov eax, ecx
	mov ebx, [coluna]
	xor edx, edx
	div ebx
	mov [valor], eax

; Escrever a string na saída
	push dword str_4
	call _printf
	add esp, 4

; Escrever a variável na saída
	push dword [valor]
	push dword fmtout
	call _printf
	add esp, 8

	mov eax, [coluna]
	mov ebx, 1
	add eax, ebx
	mov [coluna], eax

	;Salta para a instrução do rótulo passado

	jmp _L6
_L7:

	mov eax, [linha]
	mov ebx, 1
	add eax, ebx
	mov [linha], eax

	mov eax, [linha]
	mov ebx, 1
	add eax, ebx
	mov [cont2], eax

	;Salta para a instrução do rótulo passado

	jmp _L4
_L5:
_L3:

	mov eax, [linha]
	mov ebx, 1
	add eax, ebx
	mov [linha], eax

	mov eax, [linha]
	mov ebx, 1
	add eax, ebx
	mov [cont2], eax

	;Salta para a instrução do rótulo passado

	jmp _L1
_L2:
;final do programa
	mov esp,ebp
 	pop ebp
 	ret