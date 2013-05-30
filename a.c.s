.data
	str_1: .asciiz "%d\n%d\n%d\n%d\n"
.text
.align 2
.globl main
main:
	move $gp, $sp     # set global pointer (unused)
	sw $0, 0($sp)
	sw $0, -4($sp)
	addi $sp, $sp, -8 # move up a cell
	move $fp, $sp     # start using memory here
	jal Label_main
end_main:
	li $v0, 10        # exit
	syscall
Label_gcd:
	sw $31, 4($fp)
	addi $sp, $sp, -20
	lw $t1, 0($fp)
	lw $t2, -4($fp)
	rem $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $t1, -8($fp)
	li $t2, 0
	sub $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $t0, -8($fp)
	bnez $t0, Label_1
	lw $v0, -4($fp)
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_1:
	lw $t1, 0($fp)
	lw $t2, -4($fp)
	rem $t0, $t1, $t2
	sw $t0, -12($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -4($fp)
	sw $a0, -0($sp)
	lw $a1, -12($fp)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_gcd
	sw $v0, -16($fp)
Label_2:
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
	addi $sp, $sp, -20
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	li $a0, 10
	sw $a0, -0($sp)
	li $a1, 1
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_gcd
	sw $v0, 0($fp)
Label_3:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	li $a0, 50
	sw $a0, -0($sp)
	li $a1, 35
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_gcd
	sw $v0, -4($fp)
Label_4:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	li $a0, 34986
	sw $a0, -0($sp)
	li $a1, 3087
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_gcd
	sw $v0, -8($fp)
Label_5:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	li $a0, 2907
	sw $a0, -0($sp)
	li $a1, 1539
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_gcd
	sw $v0, -12($fp)
Label_6:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_1
	sw $a0, -0($sp)
	lw $a1, 0($fp)
	sw $a1, -4($sp)
	lw $a2, -4($fp)
	sw $a2, -8($sp)
	lw $a3, -8($fp)
	sw $a3, -12($sp)
	lw $t0, -12($fp)
	sw $t0, -16($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -16($fp)
Label_7:
	li $v0, 0
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
	
