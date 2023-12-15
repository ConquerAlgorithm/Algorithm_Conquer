import sys

strings = list(sys.stdin.readline().strip())
explode_strings = list(sys.stdin.readline().strip())
l = len(explode_strings)
stack = []

for i in strings:
    stack.append(i)
    if stack[len(stack) - l:len(stack)] == explode_strings:
        for _ in range(l):
            stack.pop()
if stack:
    print(''.join(stack))
else:
    print('FRULA')
