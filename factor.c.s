.data
.align 2
.globl str_0
	str_0: .asciiz "%d\n"
.globl data_gp
	data_gp: .space 0
.text
.align 2
.globl main
main:
	la $gp, data_gp
	sw $0, 0($sp)
	sw $0, -4($sp)
	addi $sp, $sp, -8 # move up a cell
	move $fp, $sp     # start using memory here
	jal Label_main
end_main:
	li $v0, 10        # exit
	syscall
Label_nfactor:
	sw $31, 4($fp)
	addi $sp, $sp, -20
	lw $t1, 0($fp)
	li $t2, 0
	seq $t0, $t1, $t2
	sw $t0, -4($fp)
	lw $t0, -4($fp)
	beqz $t0, Label_1
	li $v0, 1
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_1:
	lw $t1, 0($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -8($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -8($fp)
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_nfactor
	sw $v0, -12($fp)
Label_2:
	lw $t1, 0($fp)
	lw $t2, -12($fp)
	mul $t0, $t1, $t2
	sw $t0, -16($fp)
	lw $v0, -16($fp)
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_main:
	sw $31, 4($fp)
	addi $sp, $sp, -12
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	li $a0, 6
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_nfactor
	sw $v0, -4($fp)
Label_3:
	lw $t1, -4($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, 0($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_0
	sw $a0, -0($sp)
	lw $a1, 0($fp)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -8($fp)
Label_4:
	lw $v0, 0($fp)
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31

########################################
############### CODE GEN ###############
########################################
Label_printf:
	sw $31, 4($fp)
	addi $sp, $sp, -4
	move $v1, $a0
	addi $a1, $fp, -4
	la $s6, printf_buf 			# set s6 = base of printf buffer.

printf_loop:
	lb $a0, 0($v1)
	beq $a0, '%', printf_fmt 	# if the fmt character, then do fmt.
	beq $0, $a0, printf_end 	# if zero, then go to end.
	sb $a0, 0($s6)
	move $a0, $s6
	li $v0, 4
	syscall
printf_next:
	addi $v1, $v1, 1
	b printf_loop

printf_fmt:
	addi $v1, $v1, 1
	lb $a0, 0($v1)
	beq $a0, 'd', printf_int 	# if 'd', print as a decimal integer.
	beq $a0, 's', printf_str 	# if 's', print as a string.
	beq $a0, 'c', printf_char 	# if 'c', print as a ASCII char.
	beq $a0, '%', printf_perc 	# if '%', print a '%'
printf_int:
	lw $a0, 0($a1)
	addi $a1, $a1, -4
	li $v0, 1
	syscall
	b printf_next
printf_char:
	lw $a0, 0($a1)
	addi $a1, $a1, -4
	sb $a0, 0($s6)
	move $a0, $s6
	li $v0, 4
	syscall
	b printf_next

printf_end:						#roll back
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31

.data
printf_buf: .space 2
	
